'''
green
yellow
red
'''
import RPi.GPIO as GPIO
import time
import sys

class LED :
    RGB = {
            "OFF":(0,0,0),
            "GREEN":(0,1,0),
            "YELLOW":(1,1,0),
            "RED":(1,0,0),
            "BLUE":(0,0,1),
            "WHITE":(1,1,1),
            "PURPLE":(1,0,1)
    }
    def __init__(self) :
        # R, G, B
        self.pins = (17, 27, 22) # pin num
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BCM) # board pin x, GPIO pin o

        GPIO.setup(self.pins[0], GPIO.OUT)
        GPIO.setup(self.pins[1], GPIO.OUT)
        GPIO.setup(self.pins[2], GPIO.OUT)

    def setLED(self, color) :
        GPIO.output(self.pins[0], self.RGB[color][0])
        GPIO.output(self.pins[1], self.RGB[color][1])
        GPIO.output(self.pins[2], self.RGB[color][2])

def main() :
    led = LED()
    if len(sys.argv) > 1 :
        led.setLED(sys.argv[1])
    else :
        led.setLED("YELLOW")     

if __name__ == '__main__' :
    main()
