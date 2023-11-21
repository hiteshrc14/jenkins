#!/bin/bash

# Specify the path to the JSON file
json_file="data.json"

# Check if the file exists
if [ -f "$json_file" ]; then
    # Read JSON data from the file
    json_data=$(cat "$json_file")

    # Iterate over key-value pairs
    jq -c '. | to_entries | .[] | "\(.key): \(.value)"' <<< "$json_data"

else
    echo "Error: File $json_file not found."
fi
