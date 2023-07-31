import Adafruit_DHT

sensor = Adafruit_DHT.DHT11

pin = 2

humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)

if humidity is not None and temperature is not None:
    print('Temp={0:0.1f}*C\nHumidity={1:0.1f}%\b'.format(temperature, humidity))
else:
    print('Failed to get reading.')
