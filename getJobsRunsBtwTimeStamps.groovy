import hudson.model.*
import java.text.SimpleDateFormat
import java.util.*

def startDateStr = "2023-04-01 00:00:00"
def endDateStr = "2023-04-28 23:59:59"

// Define the time format
def sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
sdf.timeZone = TimeZone.getTimeZone("UTC")

// Convert the start and end date strings to Date objects
def startDate = sdf.parse(startDateStr)
def endDate = sdf.parse(endDateStr)

def jobCount = 0

// Get all the jobs in Jenkins
Jenkins.instance.getAllItems(AbstractProject.class).each { job ->
    // Get the last build for each job
    def lastBuild = job.lastBuild

    // Check if the last build exists and was started between the start and end date
    if (lastBuild != null && lastBuild.getTimeInMillis() >= startDate.getTime() && lastBuild.getTimeInMillis() <= endDate.getTime()) {
        jobCount++
    }
}

println "Number of jobs triggered between ${startDateStr} and ${endDateStr}: ${jobCount}"
