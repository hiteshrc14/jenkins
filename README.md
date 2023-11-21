# jenkins
json_file="data.json"

# Check if the file exists
if [ -f "$json_file" ]; then
    # Read JSON data from the file
    json_data=$(cat "$json_file")

    # Iterate over keys and values
    for key in $(echo "$json_data" | jq -r 'keys_unsorted[]'); do
        value=$(echo "$json_data" | jq -r ".$key")
        echo "Key: $key, Value: $value"
    done
