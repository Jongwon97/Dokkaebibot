from dokkaebi.atmosphere.dht11 import DHT11
from dokkaebi.atmosphere.dust import Dust
import paho.mqtt.client as mqtt
import time
import signal
import json
import multiprocessing as mp
from dokkaebi.data.info import *

class Atmosphere(mp.Process):
    """
    Atmosphere module
    """
    final_data = {"atmosphere": []}

    def __init__(self):
        """
        Atmosphere initializer
        """
        super().__init__()
        self.dht11 = DHT11()
        self.dust_module = Dust()
        signal.signal(signal.SIGTERM, self.sigterm_handler)
        self.client = mqtt.Client()
        self.client.connect(BROKER)


    def get_data(self):
        data = {}
        temp, humid = self.dht11.get_temp_humid()
        dust = self.dust_module.get_dust()
        if temp is None:
            if len(self.final_data["atmosphere"]) == 0:
                return False
            data["temperature"] = self.final_data["atmosphere"][-1]["temperature"]
        else:
            data["temperature"] = temp
        if humid is None:
            data["humidity"] = self.final_data["atmosphere"][-1]["humidity"]
        else:
            data["humidity"] = humid
        if dust is None:
            data["dust"] = self.final_data["atmosphere"][-1]["dust"]
        else:
            data["dust"] = dust
        data["time"] = int(time.time() * 1000)

        return data

    def send_data(self, data):
        # send data to mqtt broker
        self.client.publish(TOPIC_ATMOSPHERE, json.dumps(data))

    def sigterm_handler(self, signum, frame):
        # save data to file
        with open(FILE_PATH_ATMOSPHERE, "w") as f:
            json.dump(self.final_data, f)
        exit()

    def run(self):
        while True:
            data = self.get_data()
            if data is not False:
                self.send_data(data)
                self.final_data["atmosphere"].append(data)
            time.sleep(1)

def main():
    pass

if __name__ == "__main__":
    main()
