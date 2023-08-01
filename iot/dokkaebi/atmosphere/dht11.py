import Adafruit_DHT

class DHT11:
    SENSOR = Adafruit_DHT.DHT11
    PIN = 2

    def __init__(self):
        """
        DHT11 module
        """
    def read_temp_humid(self):
        humid, temp = Adafruit_DHT.read_retry(self.SENSOR, self.PIN)
        if humid is not None and temp is not None:
            print("Temp = {0:0.1f}*C\nHumidity={1:0.1f}%\b".format(temp, humid))
        else:
            print("Failed to get reading")
