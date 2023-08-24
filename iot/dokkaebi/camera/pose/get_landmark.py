import cv2
import os
from mediapipe import solutions
import json
import time
from settings import data_path

f_num = 0
frame_dict = {}
masking = ['nose', 'left_eye_inner', 'left_eye', 'left_eye_outer', 'right_eye_inner', 'right_eye', 'right_eye_outer', 'left_ear', 'right_ear',
           'mouth_left', 'mouth_right', 'left_shoulder', 'right_shoulder', 'left_elbow', 'right_elbow', 'left_wrist', 'right_wrist',
           'left_pinky', 'right_pinky', 'left_index', 'right_index', 'left_thumb', 'right_thumb']

# filepath = input("파일이 없으면 1을 입력하시오. filepath: ")
filepath = '1'
if filepath == '1':
    video = cv2.VideoCapture(-1)
else: 
    video = cv2.VideoCapture(filepath)
    length = int(video.get(cv2.CAP_PROP_FRAME_COUNT))
    width = int(video.get(cv2.CAP_PROP_FRAME_WIDTH))
    height = int(video.get(cv2.CAP_PROP_FRAME_HEIGHT))
    fps = video.get(cv2.CAP_PROP_FPS)

    try:
        if not os.path.exists(filepath[:-4]):
            os.makedirs(filepath[:-4])
    except OSError:
        print('Error: Creating directory ' + filepath[:-4])
        exit(0)

#initialize Pose estimator
mp_drawing = solutions.drawing_utils
mp_pose = solutions.pose

pose = mp_pose.Pose(
    min_detection_confidence = 0.5,
    min_tracking_confidence = 0.7
)

prev = time.time()

while video.isOpened():
    _, frame = video.read()
    cur = time.time()
    # try:
    f_num += 1
    RGB = cv2. cvtColor(frame, cv2.COLOR_BGR2RGB)
    results = pose.process(RGB)
    i = -1
    landmarks_dict = {}
    for landmark in results.pose_landmarks.landmark:
        i+= 1
        landmarks_dict[masking[i]] = {
            "x": landmark.x,
            "y": landmark.y,
            "z": landmark.z
        }
        if i == 22:
            break
    while i < 22:
        i += 1
        landmarks_dict[masking[i]] = {
            "x": 0,
            "y": 0,
            "z": 0
        }
    frame_dict.update({"frame" + str(f_num) : landmarks_dict})
    
    mp_drawing.draw_landmarks(
        frame, results.pose_landmarks, mp_pose.POSE_CONNECTIONS
    )
    cv2.imshow('Output', frame)
    

    # except Exception as e:
    #     print(e)

    if cv2.waitKey(1) == ord('q'):
        json.dump(frame_dict, open(f"{data_path}/frame_info.json", "w+", encoding="utf-8"))
        break

video.release()
cv2.destroyAllWindows()
