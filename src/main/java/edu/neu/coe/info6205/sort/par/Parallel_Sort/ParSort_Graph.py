import matplotlib.pyplot as plt
import csv

# Function to read CSV file and extract data
def read_csv(file_path):
    cutoffs = []
    times = []
    with open(file_path, 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            cutoffs.append(float(row[0]))
            times.append(float(row[1]))
    return cutoffs, times

# Read CSV files
cutoffs1, times1 = read_csv("result.csv")
cutoffs2, times2 = read_csv("result2.csv")
cutoffs3, times3 = read_csv("result3.csv")

# Plot data
plt.figure(figsize=(10, 6))
plt.plot(cutoffs1, times1, label='Result 1', marker='o')
plt.plot(cutoffs2, times2, label='Result 2', marker='o')
plt.plot(cutoffs3, times3, label='Result 3', marker='o')

# Add labels and title
plt.xlabel('Left (cutoff)')
plt.ylabel('Right (time in ms)')
plt.title('Graph of Left (cutoff) vs Right (time in ms)')
plt.legend()

# Show plot
plt.grid(True)
plt.show()
