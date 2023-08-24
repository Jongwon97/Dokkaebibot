import joblib
import pandas as pd
import cv2
from settings import data_path
from mediapipe import solutions

clf_from_joblib = joblib.load('./pose-model.pkl')
# data = pandas.read_csv(f'{data_path}/data.csv')
# x = data.iloc[:, :-1]
# y = data.loc[:, 'label']
masking = ['nose_', 'left_eye_inner_', 'left_eye_', 'left_eye_outer_', 'right_eye_inner_', 'right_eye_', 'right_eye_outer_', 'left_ear_', 'right_ear_',
           'mouth_left_', 'mouth_right_', 'left_shoulder_', 'right_shoulder_', 'left_elbow_', 'right_elbow_', 'left_wrist_', 'right_wrist_',
           'left_pinky_', 'right_pinky_', 'left_index_', 'right_index_', 'left_thumb_', 'right_thumb_']

mp_pose = solutions.pose
pose = mp_pose.Pose(
    min_detection_confidence = 0.5,
    min_tracking_confidence = 0.7
)

def predict_frame(frame):
    # try:
    RGB = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    results = pose.process(RGB)
    i = -1
    landmarks = dict()
    for landmark in results.pose_landmarks.landmark:
        i += 1
        landmarks[masking[i] + 'x'] = landmark.x
        landmarks[masking[i] + 'y'] = landmark.y
        landmarks[masking[i] + 'z'] = landmark.z
        if i == 22:
            break
    while i < 22:
        i += 1
        landmarks[masking[i] + 'x'] = 0
        landmarks[masking[i] + 'y'] = 0
        landmarks[masking[i] + 'z'] = 0
    landDict = dict()

# correct data
#     landmarks = {'nose_x': 0.5090761780738831, 'nose_y': 0.3725825548171997, 'nose_z': -0.9163085222244263, 'left_eye_inner_x': 0.5322399139404297, 'left_eye_inner_y': 0.314638614654541, 'left_eye_inner_z': -0.8510268926620483, 'left_eye_x': 0.547382116317749, 'left_eye_y': 0.3156394958496094, 'left_eye_z': -0.8510816693305969, 'left_eye_outer_x': 0.5624508857727051, 
# 'left_eye_outer_y': 0.31738901138305664, 'left_eye_outer_z': -0.8512395024299622, 'right_eye_inner_x': 0.4789511561393738, 'right_eye_inner_y': 0.3167738914489746, 'right_eye_inner_z': -0.8607879877090454, 'right_eye_x': 0.4598842263221741, 'right_eye_y': 0.3200322389602661, 'right_eye_z': -0.8602815866470337, 'right_eye_outer_x': 0.44214290380477905, 
# 'right_eye_outer_y': 0.32516610622406006, 'right_eye_outer_z': -0.8606182336807251, 'left_ear_x': 0.5857830047607422, 'left_ear_y': 0.3523796796798706, 'left_ear_z': -0.47126513719558716, 'right_ear_x': 0.4198695123195648, 'right_ear_y': 0.36353492736816406, 'right_ear_z': -0.4902153015136719, 'mouth_left_x': 0.5390740633010864, 'mouth_left_y': 0.4350942373275757, 'mouth_left_z': -0.7747159004211426, 'mouth_right_x': 0.4742019772529602, 'mouth_right_y': 0.43754589557647705, 'mouth_right_z': -0.7808651924133301, 'left_shoulder_x': 0.7553263902664185, 'left_shoulder_y': 0.7215873003005981, 'left_shoulder_z': -0.2481159269809723, 'right_shoulder_x': 0.27395039796829224, 'right_shoulder_y': 0.7460402250289917, 'right_shoulder_z': -0.4248780608177185, 'left_elbow_x': 0.862169086933136, 'left_elbow_y': 1.0429160594940186, 'left_elbow_z': -0.30100753903388977, 'right_elbow_x': 0.18389824032783508, 'right_elbow_y': 1.0811985731124878, 'right_elbow_z': -0.6087288856506348}
    landDict['frame1'] = landmarks
    df = pd.DataFrame.from_dict(landDict).reindex(landmarks.keys())
    df = df.transpose()
    # df = df.reset_index(drop=True)
    print(clf_from_joblib.predict(df)[0])
    # except:
    #     print("Error")
    #     return

if __name__ == '__main__':
    video = cv2.VideoCapture(0)
    if video.isOpened():
        _, frame = video.read()
        predict_frame(frame)

    video.release()
    cv2.destroyAllWindows()
