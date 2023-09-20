import groovy.json.JsonOutput
import groovy.json.JsonSlurper

// Define Bitbucket API URL
def bitbucketApiUrl = 'https://api.bitbucket.org/2.0'
def apiUrl = "${bitbucketApiUrl}/teams/your-bitbucket-username/projects" // Replace with your Bitbucket username

// Fetch Jenkins credentials by ID (you need to create a Secret Text credential)
def credentialsId = 'your-credentials-id' // Replace with your Jenkins credentials ID

// Inject Jenkins credentials into the script
def bitbucketAppPassword = credentials(credentialsId)

// Make an HTTP GET request to the Bitbucket API
def response = httpRequest(
    url: apiUrl,
    username: bitbucketAppPassword.username,
    password: bitbucketAppPassword.password
)

// Initialize a list to store project names
def projectNames = []

// Check the HTTP response status
if (response.getResponseCode() == 200) {
    // Parse the JSON response
    def jsonResponse = new JsonSlurper().parseText(response.getContent())
    
    // Extract project names from the response
    jsonResponse.values.each { project ->
        projectNames << project.key
    }
} else {
    // Handle error cases
    println("Failed to retrieve project data from Bitbucket API. HTTP Status Code: ${response.getResponseCode()}")
    println("Response Content:")
    println(response.getContent())
}

// Convert the project names list to a comma-separated string
def projectNamesString = projectNames.join(',')

// Print the list for debugging purposes
println("Bitbucket Projects: $projectNamesString")

// Return the project names as a string
return projectNamesString
