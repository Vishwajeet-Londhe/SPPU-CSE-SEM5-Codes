import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# Load Iris dataset
iris = pd.read_csv("/content/iris.csv")

# Extract Sepal Length and Sepal Width for example
sepal_length = iris['sepal.length']
sepal_width = iris['sepal.width']

# Compute Mean
mean_sepal_length = np.mean(sepal_length)
mean_sepal_width = np.mean(sepal_width)

# Compute Variance
var_sepal_length = np.var(sepal_length, ddof=1)  # ddof=1 for sample variance
var_sepal_width = np.var(sepal_width, ddof=1)

# Compute Standard Deviation
std_sepal_length = np.std(sepal_length, ddof=1)
std_sepal_width = np.std(sepal_width, ddof=1)

# Compute Covariance
covariance = np.cov(sepal_length, sepal_width)[0, 1]

# Compute Correlation
correlation = np.corrcoef(sepal_length, sepal_width)[0, 1]

# Compute Standard Error
n = len(sepal_length)
std_error_sepal_length = std_sepal_length / np.sqrt(n)
std_error_sepal_width = std_sepal_width / np.sqrt(n)

# Print Results
print(f"Mean Sepal Length: {mean_sepal_length}")
print(f"Mean Sepal Width: {mean_sepal_width}")
print(f"Variance Sepal Length: {var_sepal_length}")
print(f"Variance Sepal Width: {var_sepal_width}")
print(f"Standard Deviation Sepal Length: {std_sepal_length}")
print(f"Standard Deviation Sepal Width: {std_sepal_width}")
print(f"Covariance: {covariance}")
print(f"Correlation: {correlation}")
print(f"Standard Error Sepal Length: {std_error_sepal_length}")
print(f"Standard Error Sepal Width: {std_error_sepal_width}")

# Plot Distribution of Sepal Length and Sepal Width
plt.figure(figsize=(12, 6))

# Distribution plot for Sepal Length
plt.subplot(1, 2, 1)
sns.histplot(sepal_length, kde=True, color='blue')
plt.title('Distribution of Sepal Length')
plt.xlabel('Sepal Length')

# Distribution plot for Sepal Width
plt.subplot(1, 2, 2)
sns.histplot(sepal_width, kde=True, color='green')
plt.title('Distribution of Sepal Width')
plt.xlabel('Sepal Width')

plt.tight_layout()
plt.show()