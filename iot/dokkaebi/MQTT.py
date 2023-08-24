import paho.mqtt.client as mqtt
import json

class MQTT :
    def __init__(self):
        with open("dokkaebi/data/info.json") as f:
            self.info = json.load(f)

        self.client_id = self.info['SerialNumber']
        self.client = mqtt.Client(
                                  client_id = self.client_id,
                                  transport='tcp',
                                  protocol=mqtt.MQTTv5
                                  )
        print(self.info['Broker'])
        self.client.on_connect = self.on_connect
        self.client.connect(self.info['Broker'],
                            1883
                            )

        self.client.on_message = self.on_message

    def on_connect(client, userdata, flags, rc):
        if rc == 0:
            print("Connected to MQTT Broker")
        else:
            print("ERROR")

    def on_message(client, userdata, msg):
        print(msg.payload)
        return msg.payload

    def subcribe(self, topic):
        self.client.subscribe(f"{self.client_id}/{topic}")

    def publish(self, topic, payload):
        topic = f"{self.client_id}/{topic}"
        self.client.publish(f"{self.client_id}/{topic}", payload)
        print("mqtt: ", topic)
