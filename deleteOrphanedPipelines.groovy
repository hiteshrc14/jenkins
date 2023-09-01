import jenkins.branch.BranchSource
import jenkins.branch.MultiBranchProject
import jenkins.model.Jenkins
import jenkins.scm.api.SCMHead
import hudson.model.Job

// Define the Multi-Branch Pipeline name as a parameter
def multiBranchPipelineName = 'YourMultiBranchPipelineName'

// Get the Jenkins instance
def jenkins = Jenkins.instance

// Find the Multi-Branch Pipeline project by name
def multiBranchProject = jenkins.items.find { it ->
    it instanceof MultiBranchProject && it.name == multiBranchPipelineName
}

if (multiBranchProject) {
    // Iterate through all branches in the Multi-Branch Pipeline
    multiBranchProject.getItems().each { branchProject ->
        branchProject.getBuilds().each { build ->
            def branchName = build.actions.find { it instanceof SCMHead.HeadByItem }.name

            // Check if the branch still exists in the source code repository
            def branchExists = branchProject.getSourcesList().any { source ->
                source.getSource() instanceof BranchSource && source.getSource().getBranches(source, null).any { it.name == branchName }
            }

            if (!branchExists) {
                // Delete the build for the deleted branch
                println("Deleting build for branch: ${branchName}")
                build.delete()
            }
        }
    }
} else {
    println("Multi-Branch Pipeline '${multiBranchPipelineName}' not found.")
}
