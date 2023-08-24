import os
import random

path = "dokkaebi/camera/tts"
#path = "." 
#msg ={"sleep" :"일어나"}
#option = '-s 130 -p 95 -a 200 -v ko+f2'
def speak1(status):
    global option, msg
    os.system("espeak {} '{}'".format(option,msg[status]))

def speak(status) :
    num = random.randrange(1,5)
    os.system("aplay {}/{}.wav".format(path,num))
#speak("sleep")
'''
-s : speed per minute, defaut:165, range:80~165
-p : pitch, default:50, range:0~99
-a : volumn, default:100, range:0~200
-v : voice, ko+f3:korean third voice
'''

