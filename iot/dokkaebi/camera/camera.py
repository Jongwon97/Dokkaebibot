import cv2
import os
import signal
import time
import json
from dokkaebi.LED import LED
from dokkaebi.camera.pose.pose import Pose
from dokkaebi.camera.face.Face import Face
from dokkaebi.camera.object.Object import Object
from dokkaebi.data.info import *
from dokkaebi.camera.tts.tts import speak 
import paho.mqtt.client as mqtt

class Camera():
    WINDOW_SIZE = 7 
    STUDY = 0
    EMPTY = 1
    SLEEP = 2
    PHONE = 3
    BAD_STUDY = 4
    
    INCORRECT = 0
    CORRECT = 1
    MAINTAIN = -1

    MQTT_STATUS = ["good", "away", "sleep", "phone", "bad"]
    STATUS_MESSAGE=["공부중","자리비움","자는중","핸드폰중","자세불량"]
    def init(self, _lock, led_instance):
        """
        Camera module
        1. face detect - 0 sleep, 1 study, 2 empty
        2. object detect - 0 phone, 1 no phone
        3. pose detect - 0 incorrect, 1 correct
        
        
        good pose study 0, empty 1, sleep 2, phone 3, bad pose study 4 
        """
        self.led_instance = led_instance
        self.face = Face()
        self.object = Object()
        self.pose = Pose()
        self.lock = _lock
        self.updated = False

        nowTime = int(time.time() * 1000)
        self.status_list = list() 
        self.statusInfo = {"status" : self.MQTT_STATUS[self.EMPTY], "time" : nowTime}
        self.status_list.append(self.statusInfo.copy())

        # sliding window part
        self.status_cnt = [0, self.WINDOW_SIZE, 0, 0, 0]
        self.window = [self.EMPTY] * self.WINDOW_SIZE
        self.window_pointer = 0
        
        self.led_status=["GREEN","YELLOW","RED"]

        self.minTime = 3
    
    def set_mqtt(self):
        self.client = mqtt.Client()
        self.client.connect(BROKER)

    def send_data(self, status):
        self.client.publish(TOPIC_CAMERA, self.MQTT_STATUS[status])

    def signal_handler(self, signum, frame):
        if signum == signal.SIGTERM:
            self.led_instance.setLED(0, "OFF", self.lock)
            w_dict = {'camera' : self.status_list}
            with open("dokkaebi/data/camera/data.json", "w+") as outfile:
                json.dump(w_dict, outfile)
            exit(0)

    def run(self, _lock, led_instance): # main
        self.init(_lock, led_instance)
        self.set_mqtt()
        # camera setting
        cap = cv2.VideoCapture(-1)
        cap.set(cv2.CAP_PROP_FRAME_WIDTH, 416)
        cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 416)
        
        # SIGTERM activate
        signal.signal(signal.SIGTERM, self.signal_handler)
        
        while cap.isOpened():
            nowTime = int(time.time() * 1000)
            ret, frame = cap.read()  
            rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            #cv2.imshow("image", frame)
            status = self.status_check(rgb_frame, nowTime)            
            print(self.status_cnt)
            self.led_update(status)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break
        cap.release()
        cv2.destroyAllWindows()
        os.kill(os.getpid(), signal.SIGTERM)
        
    def led_update(self, status) :
        if status == self.STUDY :
            self.led_instance.setLED(0, "GREEN", self.lock)
        elif status == self.BAD_STUDY :
            self.led_instance.setLED(0, "YELLOW", self.lock)
        elif status == self.PHONE :
            self.led_instance.setLED(0, "PURPLE", self.lock)
        else :
            self.led_instance.setLED(0, "RED", self.lock)
            if self.updated == True:
                speak(status)
                self.updated = False
            
    def sliding_window(self, status) : # status : current_status
        if self.window[self.window_pointer] != status :
            self.status_cnt[status] = self.status_cnt[status] + 1
            prev_status = self.window[self.window_pointer]
            self.status_cnt[prev_status] = self.status_cnt[prev_status] - 1

        self.window[self.window_pointer] = status
        self.window_pointer = (self.window_pointer + 1) % self.WINDOW_SIZE

        major_status = self.status_cnt.index(max(self.status_cnt))
        return major_status
    
    def status_check(self, frame, nowTime) :
        current_status = self.object.getStatus(frame)
        if current_status == self.STUDY :
            current_status = self.face.getStatus(frame)
        
        
        pose_status = self.pose.getStatus(frame)
        if current_status == self.STUDY and pose_status == self.INCORRECT:    
            current_status = self.BAD_STUDY

        current_status = self.sliding_window(current_status)        
        str_current_status = self.MQTT_STATUS[current_status]
        if str_current_status == self.statusInfo["status"] : # 이전과 같은 상태면 나가
            return current_status
    
        gap = (nowTime - self.statusInfo["time"]) // 1000
        if gap < self.minTime : 
            return self.MQTT_STATUS.index(self.statusInfo["status"])
        
        self.send_data(current_status)
        if str_current_status == "sleep" :
            self.updated = True
        self.statusInfo["status"] = str_current_status
        self.statusInfo["time"] = nowTime
        self.status_list.append(self.statusInfo.copy())
        return current_status
    
