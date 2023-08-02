from dokkaebi.atmosphere.dht11 import DHT11

class Atmosphere:
    """
    Atmosphere module
    """
    def __init__(self):
        """
        Atmosphere Constructor
        """
        self.__dht11 = DHT11()

    def get_atmosphere_data(self):
        self.__dht11.read_temp_humid()
    

def main():
    at = Atmosphere()
    at.get_atmosphere_data()
