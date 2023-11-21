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
