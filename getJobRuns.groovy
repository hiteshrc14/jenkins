import jenkins
import datetime

# set up the Jenkins server connection
server = jenkins.Jenkins('http://<your_jenkins_url>/', username='<your_username>', password='<your_api_token>')

# calculate the date 30 days ago
thirty_days_ago = datetime.datetime.today() - datetime.timedelta(days=30)

# get the list of job runs for the last 30 days
job_runs = server.get_all_jobs_info(depth=1)

# count the number of job runs in the last 30 days
num_runs = 0
for job in job_runs:
    runs = server.get_builds(job['name'])
    for run in runs:
        if datetime.datetime.fromtimestamp(run['timestamp'] / 1000) > thirty_days_ago:
            num_runs += 1

print(f"Number of job runs in the last 30 days: {num_runs}")
