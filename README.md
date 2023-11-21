#!/bin/bash

# Specify the path to the JSON file
json_file="data.json"

# Check if the file exists
if [ -f "$json_file" ]; then
    # Loop through key-value pairs and store in variables
    while IFS= read -r line; do
        key=$(jq -r '.key' <<< "$line")
        value=$(jq -r '.value' <<< "$line")

        # Do something with the key and value (replace with your logic)
        echo "Key: $key, Value: $value"
    done < <(jq -c 'to_entries | .[]' < "$json_file")
else
    echo "Error: File $json_file not found."
fi



import subprocess
import json

def run_aws_command(command):
    try:
        result = subprocess.run(command, check=True, capture_output=True, text=True)
        return json.loads(result.stdout.strip())
    except subprocess.CalledProcessError as e:
        print(f"Error executing AWS CLI command: {e}")
        return None

# Example command: aws ec2 describe-subnets
aws_command = ["aws", "ec2", "describe-subnets", "--query", "Subnets[?Tags[?Key=='Name' && starts_with(Value, 'Public')]].CidrBlock", "--output", "json"]
output = run_aws_command(aws_command)

if output:
    print(json.dumps(output, indent=2))
