#!/usr/bin/groovy
@Library('github.com/Sphereon/fabric8-pipeline-library@master') _
def sphereon = new io.fabric8.SphereonUtils()
sphereon.init()

def canaryVersion = "1.0.${env.BUILD_NUMBER}"
def utils = new io.fabric8.Utils()
node {

 // Checkout code from repository
    stage('Checkout source') {
        checkout scm
    }


    def branch = utils.getBranch();
    def branchType = getBranchType(branch)
    def environment = getDeploymentEnvironmentFromBranchType(branchType)

    echo "======> Building branch ${branch} of type ${branchType} for env ${environment}"


    stage('Build and Unit tests') {
        // Maven installation declared in the Jenkins "Global Tool Configuration"
    	// withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe reports and FindBugs reports
		withMaven(maven: 'M3') {
            // Run the maven build (works on both linux and windows)
			sh "mvn -e -U clean test"
		}
	}


    if (utils.isCI()){
        echo'###########################################'
        echo'############ Build '+ canaryVersion
        echo'###########################################'
        stage('Deploy') {
            // Maven installation declared in the Jenkins "Global Tool Configuration"
        	// withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe reports and FindBugs reports
    		withMaven(maven: 'M3') {
                // Run the maven build (works on both linux and windows)
    			sh "mvn deploy"
    		}
    	}
    } else if (utils.isCD()){
        echo 'NOTE: running pipelines for the first time will take longer as build and base docker images are pulled onto the node'
        container(name: 'maven') {

            stage('Build Release'){
                mavenCanaryRelease {
                    version = canaryVersion
                }
            }
        }
    } else {
        echo "###############NOPE##################"
    }
}


def getBranchType() {
    return getBranchType(new io.fabric8.Utils().getBranch())
}
def getBranchType(String branchName) {
    def devPattern = ".*develop.*"
    def releasePattern = ".*release.**"
    def featurePattern = ".*feature.*"
    def hotfixPattern = ".*hotfix.*"
    def masterPattern = ".*master.*"
    if (branchName =~ devPattern) {
        return "dev"
    } else if (branchName =~ releasePattern) {
        return "release"
    } else if (branchName =~ masterPattern) {
        return "master"
    } else if (branchName =~ featurePattern) {
        return "feature"
    } else if (branchName =~ hotfixPattern) {
        return "hotfix"
    } else {
        return "dev";
    }
}

def getDeploymentEnvironmentFromBranchType(String branchType) {
    if (branchType == "dev") {
        return "development"
    } else if (branchType == "release") {
        return "staging"
    } else if (branchType == "master") {
        return "production"
    } else {
        return "development";
    }
}
