mport jenkins.model.Jenkins

// Define the Multi-Branch Pipeline name as a parameter
def multiBranchPipelineName = 'YourMultiBranchPipelineName'

// Get the Jenkins instance
def jenkins = Jenkins.instance

// Find the Multi-Branch Pipeline project by name
def multiBranchProject = jenkins.items.find { it ->
    it.displayName == multiBranchPipelineName && it instanceof org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject
}

if (multiBranchProject) {
    // List existing branch names in the source code repository
    def existingBranches = [:]
    
    // You should customize this logic to fetch the list of existing branches from your repository
    // Here, we assume you have a function called getExistingBranches() that returns a list of existing branch names
    existingBranches = getExistingBranches() // Replace with your logic
    
    // Iterate through all branch jobs within the Multi-Branch Pipeline project
    multiBranchProject.getItems().each { branchJob ->
        def branchName = branchJob.name
        
        // Check if the branch still exists in the source code repository
        if (!existingBranches.contains(branchName)) {
            // Delete the job for the deleted branch
            println("Deleting job for branch: ${branchName}")
            branchJob.delete()
        }
    }
} else {
    println("Multi-Branch Pipeline project '${multiBranchPipelineName}' not found or is not a WorkflowMultiBranchProject.")
}
