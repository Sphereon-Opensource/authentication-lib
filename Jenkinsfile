#!/usr/bin/groovy
@Library('pipeline-library@master')

def utils = new io.fabric8.Utils()

mavenNode {
 // Checkout code from repository
    stage('Checkout source') {
        checkout scm

    }

    buildLibrary{}


}


