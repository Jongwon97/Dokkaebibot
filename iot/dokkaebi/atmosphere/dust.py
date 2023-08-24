import serial
from time import sleep

class Dust:
    def __init__(self):
        """
        GP2Y1010AU0F
        """
        self.ser = serial.Serial('/dev/ttyACM0', 9600, timeout=1)
        self.ser.reset_input_buffer()

    def get_dust(self):
        while True:
            if self.ser.in_waiting > 0:
                result = self.ser.readline().decode('utf-8').rstrip()
                try :
                    ret = float(result)
                    return ret
                except:
                    return None
                #print(line)
    
    def test(self):
        while True:
            if self.ser.in_waiting > 0:
                result = self.ser.readline().decode('utf-8').rstrip()
            sleep(1)
        

if __name__ == '__main__':
    d = Dust()
    d.test()
