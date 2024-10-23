import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler
from sklearn.mixture import GaussianMixture
from sklearn.metrics.cluster import adjusted_rand_score
import warnings
warnings.filterwarnings("ignore")
%matplotlib inline

iris=pd.read_csv("https://media.githubusercontent.com/media/neurospin/pystatsml/refs/heads/master/datasets/iris.csv")
iris

x= iris.iloc[:,:4]
y= iris.iloc[:,-1]
sc =StandardScaler()
sc.fit(x)
std_array =sc.transform(x)
X = pd.DataFrame(std_array,columns = x.columns)

cluster = GaussianMixture(n_components=3)
cluster.fit(X)
y_pred =cluster.predict(X)
score = adjusted_rand_score(y,y_pred)
score

from sklearn.decomposition import PCA

pca =PCA(n_components=2)
pca_array = pca.fit_transform(iris.drop(['species'], axis=1))
pca_df=pd.DataFrame(pca_array,columns=["PC1","PC2"])
pca_df.head()

col_code = {0:"yellow", 1:"darkblue",2:"green"} 
label = {0:"setosa", 1:"versicolor",2:"virginica"}

pca_df["labels"]= pd.DataFrame(y_pred) 
groups = pca_df.groupby('labels')

# Grouping instances based on species
groups.mean()

fig, ax = plt.subplots(1, 1, figsize=(15, 10))
for name, group in groups:
    ax.plot(group.PC1, group.PC2, color=col_code[name], label=label[name], marker='o', linestyle="", ms=10)

ax.legend()
plt.show()
