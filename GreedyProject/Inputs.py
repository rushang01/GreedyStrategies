import random

# Number of bags and extra working devices
n = 5000
k = 100

# Generate random input data for bags
input_string = f"{n} {k}\n"

for i in range(n):
    num_working = random.randint(0, 100)
    total = random.randint(num_working, 200)
    input_string += f"{num_working} {total}\n"

# Print the generated input string
print(input_string)

file_name = "input5.txt"

with open(file_name,"w") as file:
    file.write(input_string)