
import matplotlib.pyplot as plt
import numpy as np
import csv

# Load grids in here
grid_a = np.loadtxt(open("test3.csv","r"), delimiter=",")
grid_b = np.loadtxt(open("perfect_image_real.csv","r"), delimiter=",")
# Calculate the relative difference between grid_a and grid_b (in percentage)
rel_diff = np.sqrt(((grid_a-grid_b) ** 2).sum())/np.sqrt((grid_a ** 2).sum())
print("Relative difference: %f" % rel_diff)

# Create figure instance
f = plt.figure()

# # Add grid_a to figure
f.add_subplot(1,2,1)
plt.title('Grid A')
plt.imshow(grid_a, cmap='hot', interpolation='none')
plt.colorbar()

# Add grid_b to figure
f.add_subplot(1, 2, 2)
plt.title('Clean')
plt.imshow(grid_b, cmap='hot', interpolation='none')
plt.colorbar()

# Show plot
plt.show()
