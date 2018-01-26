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

/**
    def branch = utils.getBranch();
    def branchType = getBranchType(branch)
    def environment = getDeploymentEnvironmentFromBranchType(branchType)

    echo "======> Building branch ${branch} of type ${branchType} for env ${environment}"
*/

    podTemplate(label: 'mypod') {
            node('mypod') {
            stage('Run shell in pod') {
                sh 'echo hello world'
            }
        }
    }



    stage('Build and Unit tests') {
        // Maven installation declared in the Jenkins "Global Tool Configuration"
    	// withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe reports and FindBugs reports
		withMaven(maven: 'M3') {
            // Run the maven build (works on both linux and windows)
			sh "mvn -e -U clean deploy"
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


