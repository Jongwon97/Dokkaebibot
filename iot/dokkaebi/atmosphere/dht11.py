import Adafruit_DHT

class DHT11:
    SENSOR = Adafruit_DHT.DHT11
    PIN = 2

    def __init__(self):
        """
        DHT11 module
        """

    def get_temp_humid(self):
        """
        Read temperature and humidity from DHT11

        Returns
        -------
        temp : float
            Temperature
        humid : float
            Humidity
        """

        humid, temp = Adafruit_DHT.read_retry(self.SENSOR, self.PIN)
        return temp, humid
        # if humid is not None and temp is not None:
            # print("Temp = {0:0.1f}*C\nHumidity={1:0.1f}%\b".format(temp, humid))
            # return temp, humid
        # else:
            # return None, None
            # print("Failed to get reading")
