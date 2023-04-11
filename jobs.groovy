import groovy.json.JsonSlurper

// Make an HTTP GET request to the Jenkins REST API to get the list of all pipelines
def url = 'http://<YOUR_JENKINS_URL>/api/json?tree=jobs[name,url,builds[number,result,duration,timestamp]]'
def json = new JsonSlurper().parseText(new URL(url).text)

// Loop through all pipelines and get the duration of the last successful build
json.jobs.each { pipeline ->
    def pipelineName = pipeline.name
    def pipelineUrl = pipeline.url

    // Check if the pipeline has any builds
    if (pipeline.builds) {
        def lastSuccessfulBuild = pipeline.builds.find { it.result == 'SUCCESS' }

        // Check if the pipeline has a last successful build
        if (lastSuccessfulBuild) {
            def buildNumber = lastSuccessfulBuild.number
            def buildDuration = lastSuccessfulBuild.duration
            def buildTimestamp = lastSuccessfulBuild.timestamp

            // Convert the build duration to minutes
            def buildDurationMinutes = buildDuration / (1000 * 60)

            println "Pipeline Name: ${pipelineName}"
            println "Pipeline URL: ${pipelineUrl}"
            println "Last Successful Build Number: ${buildNumber}"
            println "Last Successful Build Duration (minutes): ${buildDurationMinutes}"
            println "Last Successful Build Timestamp: ${buildTimestamp}\n"
        }
    }
}
