import dokkaebi
from time import sleep

at = dokkaebi.Atmosphere()
at.init()

while True:
    at.get_atmosphere_data()
    at.get_dust_data()
    print(at.temp)
    print(at.humid)
    print(at.dust)
    sleep(1)
