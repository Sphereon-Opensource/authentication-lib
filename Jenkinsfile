#!groovy
@Library('github.com/Sphereon/fabric8-pipeline-library@master')
import io.fabric8.SphereonUtils
import io.fabric8.Utils


node() {


    // Checkout code from repository
    stage('Checkout source') {
        checkout scm
    }


     echo 'Utils is CI ' + Utils.isCI()
     echo 'Sphereon is CI ' + SphereonUtils.isCI()
     echo 'is CD ' + isCD()

    stage('Build authentication-lib') {
		withMaven(
				// Maven installation declared in the Jenkins "Global Tool Configuration"
				maven: 'M3')
			{

            // Run the maven build (works on both linux and windows)
			sh "mvn clean install"

		}
		// withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe reports and FindBugs reports
	}
}