import jenkins.model.Jenkins

// Define the Multi-Branch Pipeline project name as a parameter
def multiBranchPipelineName = 'YourMultiBranchPipelineName'

// Get the Jenkins instance
def jenkins = Jenkins.instance

// Find the Multi-Branch Pipeline project by name
def multiBranchProject = jenkins.getItem(multiBranchPipelineName)

if (multiBranchProject) {
    // Iterate through all branch jobs within the Multi-Branch Pipeline project
    multiBranchProject.getItems().each { branchJob ->
        branchJob.getBuilds().each { build ->
            def branchName = build.getDisplayName()

            // Check if the branch still exists in the source code repository
            // You should customize this logic based on how you determine if a branch is deleted
            boolean branchExists = true  // Replace with your logic

            if (!branchExists) {
                // Delete the build for the deleted branch
                println("Deleting build for branch: ${branchName}")
                build.delete()
            }
        }
    }
} else {
    println("Multi-Branch Pipeline project '${multiBranchPipelineName}' not found.")
}
