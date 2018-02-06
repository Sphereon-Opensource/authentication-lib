#!/usr/bin/groovy
@Library('pipeline-library@master')

mavenNode {
 // Checkout code from repository
    stage('Checkout source') {
        checkout scm

    }

    buildLibrary{}
}


