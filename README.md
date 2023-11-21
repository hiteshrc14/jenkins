# jenkins
aws ec2 describe-subnets --query "Subnets[?Tags[?Key=='Name' && starts_with(Value, 'Public')]].CidrBlock" --output json
