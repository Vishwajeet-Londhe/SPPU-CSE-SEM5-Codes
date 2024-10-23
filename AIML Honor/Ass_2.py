from scipy.stats import skew, kurtosis
import matplotlib.pyplot as plt
import scipy.stats as stats
import pandas as pd
import numpy as np
import warnings
warnings.filterwarnings("ignore")

x=np.array([60,70,75,80,50,72,78,65,75,92,82,68,71,77,76,84])

mean= np.mean(x)
sd = np.std(x,ddof=1)
median = np.median(x)
fit = stats.norm.pdf(x,mean,sd)
plt.hist(x, density = True, color="maroon", ec ="white",)
plt.plot(x,fit,"go:")
plt.title("Math Scores")
plt.xlabel("Marks")
plt.ylabel("Normal Distribution")

plt.show()

def skewness(x,mean,sd):
    return np.sum((x-mean)**3)/((len(x)-1)*(sd**3))

def kurtosis(x,mean,sd):
    return np.sum((x-mean)**4)/((len(x)-1)*(sd**4))

skewness(x,mean,sd)
kurtosis(x,mean,sd)
