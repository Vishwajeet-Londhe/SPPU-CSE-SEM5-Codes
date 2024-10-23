import numpy as np
import pandas as pd
from sklearn.linear_model import LinearRegression
import matplotlib.pyplot as plt

df= pd.read_csv("/content/birthwt.csv")
def calc_covariance(dataset1,dataset2):
    '''
Def: Covariance measures the relationship trend between two sets of data.
Formula:1) Σ((Χ - X_mean)*(Y-Y_mean))/n
    '''
    mean1= np.mean(dataset1)
    mean2 = np.mean(dataset2)
    return np.sum(np.multiply(dataset1-mean1,dataset2-mean2))/len(dataset1)
def correlation(dataset1,dataset2):
    '''
Def: Covariance measures the relationship trend between two sets of data. Formula:1) cov(x,y)/(std(x)*std(y))
    '''
cov=calc_covariance(dataset1,dataset2)
sd1 = np.std(dataset1)
sd2= np.std(dataset2)
return cov/(sd1*sd2)

# Age of mother 
age = df["age"] 
age = age.to_numpy()

#Birth weight in grams 
birthwt = df["bwt"] 
birthwt = birthwt.to_numpy()

correlation(age, birthwt)

# Converting birth weight from gram to kg for better scaling 
plt.scatter(age,birthwt/1000,c="green") 
plt.xlabel("Age")
plt.ylabel("Birth weight(Kg)")

Ir = LinearRegression()
age = age.reshape(-1,1)
Ir.fit(age,birthwt)

y = Ir.predict(age)
print("Coefficients:",lr.coef_[0])
print("intercept:", lr.intercept_)

plt.plot(age,y,color="red")
plt.scatter(age,birthwt,c= "green")
plt.xlabel("Age")
plt.ylabel("Birth weight(g)")
plt.show()

#Mother's weight during last menstrual period. (in pounds)
motherswt = df["lwt"]
motherswt =motherswt.to_numpy()

#converting in grams to pounds
birthwt = birthwt/454
correlation(motherswt,birthwt)
plt.xlabel("Mother's weight")
plt.ylabel("Birth weight (Kg)")
plt.scatter(motherswt,birthwt,c = "darkblue")

motherswt =motherswt.reshape(-1,1) 
lr.fit(motherswt,birthwt)

z = lr.predict(motherswt) 
print("Coefficients:",lr.coef_[0]) 
print("intercept :", lr.intercept_)

plt.plot(motherswt,z,c="orange") 
plt.scatter(motherswt,birthwt,c="darkblue") 
plt.xlabel("Mother's weight") 
plt.ylabel("Birth weight")
plt.show()