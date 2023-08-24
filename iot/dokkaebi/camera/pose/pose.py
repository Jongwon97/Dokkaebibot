import joblib
import pandas as pd
import cv2
from mediapipe import solutions
from datetime import datetime

class Pose:
    TIMES_LENGTH = 5 # 한 번 판단할 사이즈
    HALF_LENGTH = TIMES_LENGTH / 2
    MASKING = ['nose_', 'left_eye_inner_', 'left_eye_', 'left_eye_outer_', 'right_eye_inner_', 'right_eye_', 'right_eye_outer_', 'left_ear_', 'right_ear_',
           'mouth_left_', 'mouth_right_', 'left_shoulder_', 'right_shoulder_', 'left_elbow_', 'right_elbow_', 'left_wrist_', 'right_wrist_',
           'left_pinky_', 'right_pinky_', 'left_index_', 'right_index_', 'left_thumb_', 'right_thumb_']
    
    def __init__(self):
        """
        Pose module
        """
        self.clf_from_joblib = joblib.load('dokkaebi/camera/pose/data/pose-model.pkl')
        self.mp_pose = solutions.pose
        self.pose = self.mp_pose.Pose(
            min_detection_confidence = 0.5,
            min_tracking_confidence = 0.7
        )
        self.status_cnt = [0, self.TIMES_LENGTH] # 0 - incorrect, 1 - correct
        self.recent_times = [1]*self.TIMES_LENGTH
        self.pointer = 1
        self.cur_status = 1

    def getStatus(self, frame):
        return self._isGood(frame)
        # ret = self._getStatus(frame)
        # if self.cur_status != ret:
        #     self.cur_status = ret
        #     # return ret
        # return ret # 상태 변화 없음

    def _getStatus(self, frame):
        try:
            headTime = self.pointer
            self.pointer = (headTime + 1) % self.TIMES_LENGTH
            #print(self.recent_times)
            cur = headTime - 1
            if cur < 0:
                cur = self.TIMES_LENGTH - 1
            cur_status = self._isGood(frame)
            last_status = self.recent_times[cur]
            self.recent_times[cur] = cur_status
            # print(cur_status, '|' , last_status)
            if last_status != cur_status:
                self.status_cnt[cur_status] = self.status_cnt[cur_status] + 1
                self.status_cnt[last_status] = self.status_cnt[last_status] - 1

            if self.status_cnt[0] >= self.HALF_LENGTH:
                return 0
            return 1
        except:
            return 0


    def _isGood(self, frame):
        try: 
            results = self.pose.process(frame)
            i = -1
            landmarks = dict()
            for landmark in results.pose_landmarks.landmark:
                i += 1
                landmarks[self.MASKING[i] + 'x'] = landmark.x
                landmarks[self.MASKING[i] + 'y'] = landmark.y
                landmarks[self.MASKING[i] + 'z'] = landmark.z
                if i == 22:
                    break
            while i < 22:
                i += 1
                landmarks[self.MASKING[i] + 'x'] = 0
                landmarks[self.MASKING[i] + 'y'] = 0
                landmarks[self.MASKING[i] + 'z'] = 0
            landDict = dict()

            landDict['frame1'] = landmarks
            df = pd.DataFrame.from_dict(landDict).reindex(landmarks.keys())
            df = df.transpose()
            return self.clf_from_joblib.predict(df)[0]
        except Exception as e:
            return 0


if __name__ == '__main__':
    video = cv2.VideoCapture(0)
    p = Pose()
    while video.isOpened():
        _, frame = video.read()
        rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        cv2.imshow("image", frame)
        p.getStatus(rgb_frame)
        if cv2.waitKey(1) == ord('q'):
            break
    video.release()
    cv2.destroyAllWindows()
