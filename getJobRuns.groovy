import hudson.model.*
import jenkins.model.*

// Get the current Jenkins instance
Jenkins jenkins = Jenkins.getInstance()

// Define the time range (last 30 days)
Calendar cal = Calendar.getInstance()
cal.add(Calendar.DATE, -30)
Date startDate = cal.getTime()

// Get all jobs in the Jenkins instance
List<Job> allJobs = jenkins.getAllItems(Job.class)

// Initialize a counter
int numRuns = 0

// Loop through each job and count the number of runs in the time range
for (Job job : allJobs) {
    def builds = job.getBuilds().byTimestamp(startDate.getTime(), System.currentTimeMillis())
    numRuns += builds.size()
}

// Print the total number of runs
println "Total number of job runs in the last 30 days: $numRuns"
