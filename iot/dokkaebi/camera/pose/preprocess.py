import pandas as pd
import json
import os
from settings import data_path

# open data
df = pd.DataFrame()
for ol in os.listdir(data_path):
    if ol[-4:] != 'json':
        continue
    with open(f'{data_path}/{ol}', 'r') as fr:
        raw = json.load(fr)
    # set label as 1 if pose is correct
    label = 1 if 'good' in ol else 0
    # change raw json data into dataframe
    temp = dict()
    for frame in raw.keys():
        temp[frame] = dict()
        # print(raw[frame].keys()) -> masking 값
        for body in raw[frame].keys():
            # print(raw[frame][body].keys()) -> x, y, z
            for coordinate in raw[frame][body].keys():
                key_string = f"{body}_{coordinate}"
                temp[frame][key_string] = raw[frame][body][coordinate]
    df_temp = pd.DataFrame(temp)
    df_temp = df_temp.transpose()
    # add label column
    df_temp['label'] = label
    df_temp = df_temp.reset_index(drop=True)
    # concat to one df
    df = pd.concat([df, df_temp], axis=0, ignore_index=True)
# save
df.to_csv(f'{data_path}/data.csv', index=False)
