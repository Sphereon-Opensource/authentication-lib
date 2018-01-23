#!/usr/bin/groovy
@Library('github.com/Sphereon/fabric8-pipeline-library@master') _

def canaryVersion = "1.0.${env.BUILD_NUMBER}"
def utils = new io.fabric8.Utils()
mavenNode {
  checkout scm
  if (utils.isCI()){

  echo'###########################################'
  echo'############ Version '+ canaryVersion
  echo'###########################################'
/*
    mavenCI{}
**/
  } else if (utils.isCD()){

    echo 'NOTE: running pipelines for the first time will take longer as build and base docker images are pulled onto the node'
  /*  container(name: 'maven') {

      stage('Build Release'){
        mavenCanaryRelease {
          version = canaryVersion
        }
      }
    }*/
  } else {
    echo "###############NOPE##################"
  }
}