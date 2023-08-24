import RPi.GPIO as GPIO
import multiprocessing as mp
import sys

class LED(object):
    RGB = {
            "OFF":(0,0,0),
            "GREEN":(0,1,0),
            "YELLOW":(1,1,0),
            "RED":(1,0,0),
            "BLUE":(0,0,1),
            "WHITE":(1,1,1),
            "PURPLE":(1,0,1)
    }
    PINS = (17, 27, 22) # pin num

    def __new__(cls):
        if not hasattr(cls, 'instance'):
            cls.instance = super(LED, cls).__new__(cls)
            #cls.instance.init_led()
            print("new LED")
        else:
            print("recycle LED")
        return cls.instance
    
    def __init__(self):
        print("LED init")
        self.manager = mp.Manager()
        self.flag = self.manager.Value('b', False)
        self.init_led()

    def init_led(self):
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BCM) # board pin x, GPIO pin o

        GPIO.setup(self.PINS[0], GPIO.OUT)
        GPIO.setup(self.PINS[1], GPIO.OUT)
        GPIO.setup(self.PINS[2], GPIO.OUT)
        self.flag.value = False
    
    def setLED(self, locate, color, lock):
        VOICE = 1
        print(f"LED().flag {locate} {self.flag.value}")

        lock.acquire()
        if self.flag.value and locate != VOICE:
            lock.release()
            return
        if locate == VOICE:
            self.flag.value = not self.flag.value
        if color == "OFF":
            self.flag.value = False
        lock.release()

        GPIO.output(self.PINS[0], self.RGB[color][0])
        GPIO.output(self.PINS[1], self.RGB[color][1])
        GPIO.output(self.PINS[2], self.RGB[color][2])

