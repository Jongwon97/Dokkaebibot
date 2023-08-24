import cv2
import numpy as np
from time import sleep
from dokkaebi.camera.object.settings import data_path
class Object:
    PERSON = 0
    CELL_PHONE = 67
    
    PHONE = 0
    EMPTY = 1

    def __init__(self):
        self.net = cv2.dnn.readNet(f"{data_path}/yolov3-tiny.weights", f"{data_path}/yolov3-tiny.cfg")
        self.classes = []
        with open(f"{data_path}/coco.names","r") as f:
            self.classes = [line.strip() for line in f.readlines()] 
        layer_names = self.net.getLayerNames()
        self.output_layers = [ layer_names[i-1] for i in self.net.getUnconnectedOutLayers()]

        # 0 : using phone 1 : no phone
        self._times_length=8 # sliding window frame 크기
        self.status = [0, self._times_length] # sliding winodw 안에 있는 각 status 개수
        self.recent_times=[self.EMPTY]*self._times_length # default (not study) 상태로 window를 다 채움
        self.head=1 # 맨앞 요소
        self.currentStatus = -1 
    
    def getStatus(self, img) :
        # ret = self._slidingWindows(img)
        ret = self._detectPhone(img)
        if ret == self.PHONE : return 3
        else : return 0
  
    
    def _slidingWindows(self, img) :
        headTime = self.head
        self.head = (headTime+1) % self._times_length
        current = headTime - 1
        if current < 0 :
            current = self._times_length-1
            
        currentStatus = self._detectPhone(img)
  
        lastStatus=self.recent_times[current]
        self.recent_times[current] = currentStatus
        if lastStatus != currentStatus :
            self.status[currentStatus] = self.status[currentStatus] + 1
            self.status[lastStatus] = self.status[lastStatus] - 1 

        if self.status[self.PHONE] >= self._times_length / 4 :
            #폰 사용중
            return self.PHONE
        #사용 안 하는중
        return self.EMPTY

    def _detectPhone(self, img):
        # img = cv2.resize(img, None, fx=0.4, fy=0.4) # 이미지 크기를 재설정한다
        
        height, width, channels = img.shape # 이미지의 속성들을 넣는다.
        blob = cv2.dnn.blobFromImage(img, 0.00392, (416,416), (0, 0, 0), True, crop=False)
        self.net.setInput(blob)
        outs = self.net.forward(self.output_layers)

        isCellPhone=False
        for out in outs:
            for detection in out:
                scores = detection[5:]
                #class_id = np.argmax(scores) # scores 중에서 최대값을 색인하여 class_id에 넣는다.
                class_id = self.CELL_PHONE
                confidence = scores[class_id]
                if confidence > 0.1 :
                    isCellPhone = True
                    break
                #if class_id==self.CELL_PHONE and confidence > 0.2 : # 만약 정확도가 0.5가 넘는다면 사물이 인식되었다고 판단
                #    isCellPhone=True              
                #    break
        if isCellPhone :
            # using cellphone
            return self.PHONE
        else :  
            # no cellphone
            return self.EMPTY


def main():
    obj=Object()
    cap=cv2.VideoCapture(0)
    # cap.set(cv2.CAP_PROP_FRAME_WIDTH, 416)
    # cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 416)
    while True :
        ret, img = cap.read()

        cv2.imshow("img",img)
        ans = obj.getStatus(img)
        
        if ans == 3 :
            print("using phone")
        else :
            print("no phone")
                
        # cv2.imshow("img",img)
        if cv2.waitKey(1) & 0xFF == ord('q'):
                break
        # sleep(0.1)
    cap.release()
    cv2.destroyAllWindows()

if __name__=='__main__' :
    main()
#test
