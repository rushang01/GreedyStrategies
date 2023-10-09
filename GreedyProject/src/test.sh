#!/bin/bash

# Check if the group number is provided as a command line argument
if [ $# -eq 0 ]; then
  echo "Usage: $0 <group_number>"
  exit 1
fi

group_number=$1

# Create a folder with the group number
folder_name="Temp-Group${group_number}-gtf65dg"
mkdir "$folder_name"

# Unzip the file into the folder
unzip "Group${group_number}.zip" -d "$folder_name"

# Change directory to the folder
cd "$folder_name"

# Define the input string
input_string=$'10 8\n2 4\n3 9\n4 5\n2 8\n2 4\n2 10\n1 4\n3 7\n1 3\n4 6'

# Define the expected output strings for each task
expected_outputs=("6 8 0 3 4 5 6 8" "5 3 6 5 1 3 5 8" "8 0 4 6 8 0 2 4" "8 6 0 4 6 8 3 5")

# Function to run a task and compare its output
function grade_task {
  task_number=$1
  expected_output="${expected_outputs[$task_number - 1]}"

  # Run the task using 'make runX' and capture the output
  task_output=$(make -s run$task_number <<< "$input_string")

  # Remove trailing newline characters from both outputs
  task_output=$(echo -n "$task_output" | sed -e 's/[[:space:]]*$//')
  expected_output=$(echo -n "$expected_output" | sed -e 's/[[:space:]]*$//')

  # Compare the output with the expected output
  echo "Task $task_number Ouput: $task_output"
  if [ "$task_output" == "$expected_output" ]; then
    echo "Task $task_number Status: Passed"
  else
    echo "Task $task_number: Failed"
  fi
}

# Compile the code using 'make'
make -s

# Loop through each task and grade it
for task_num in {1..4}; do
  grade_task $task_num
done

# Change back to the original directory
cd ..

# Delete the folder and its contents
rm -rf "$folder_name"