import groovy.json.JsonSlurper
import groovy.util.Base64

// Define your Bitbucket API URL and authentication credentials
def bitbucketUrl = 'https://api.bitbucket.org/2.0'
def username = 'your_username'
def password = 'your_password'

// Encode the credentials for Basic Authentication
def authString = "${username}:${password}"
def authBytes = authString.bytes
def authEncoded = Base64.encodeBase64String(authBytes)

// Define the Bitbucket API endpoint you want to call
def endpoint = '/repositories/your_username/your_repo'

// Create the HTTP request URL
def apiUrl = "${bitbucketUrl}${endpoint}"

// Create an HTTP connection
def connection = apiUrl.toURL().openConnection() as HttpURLConnection

// Set the request method to GET
connection.requestMethod = 'GET'

// Set the Authorization header for Basic Authentication
connection.setRequestProperty('Authorization', "Basic ${authEncoded}")

// Get the response code
def responseCode = connection.responseCode

// Check if the request was successful (HTTP status 200)
if (responseCode == 200) {
    // Read and parse the JSON response
    def responseText = connection.inputStream.text
    def jsonResponse = new JsonSlurper().parseText(responseText)
    
    // Process the JSON response here
    println "Response: ${jsonResponse}"
} else {
    println "Failed to fetch data from Bitbucket API. HTTP Status Code: ${responseCode}"
}
