import pandas as pd
import numpy as np
from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
from settings import data_path
import joblib

# read data & split
data = pd.read_csv(f'{data_path}/data.csv')
X = data.iloc[:, :-1]  # all columns except 'label'
y = data.loc[:, 'label']
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.3, random_state=42, stratify=y)

# make model & start training
svm = SVC(kernel='linear', C=1.0, random_state=42)
svm.fit(X_train, y_train)

# print
print(svm.score(X_test, y_test))

# es
# svm = SVC(kernel='linear', C=1.0, random_state=42)
# svm.fit(X, y)
#
# data_es = pd.read_csv(f'{data_path}/data_es.csv')
# X_es = data_es.iloc[:, :-1]
# y_es = data_es.loc[:, 'label']
# print(svm.score(X, y))
# print(svm.score(X_es, y_es))

joblib.dump(svm, f'{data_path}/pose-model.pkl')
