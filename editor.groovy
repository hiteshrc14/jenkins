import jenkins.branch.MultiBranchProject
import jenkins.model.Jenkins

// Get the Jenkins instance
def jenkins = Jenkins.instance

// List all Multi-Branch Pipeline projects
def multiBranchProjects = jenkins.getAllItems(MultiBranchProject.class)

if (multiBranchProjects) {
    println("List of Multi-Branch Pipeline projects:")
    multiBranchProjects.each { project ->
        println("- ${project.displayName} (${project.name})")
    }
} else {
    println("No Multi-Branch Pipeline projects found.")
}
