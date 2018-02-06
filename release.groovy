#!/usr/bin/groovy

def updateDependencies(source){

    def properties = []
    properties << ['<mockwebserver.version>','io/fabric8/mockwebserver']

    updatePropertyVersion{
        updates = properties
        repository = source
        project = 'Sphereon-Opensource/authentication-lib'
    }
}

def stage(){
    return stageProject{
        project = 'Sphereon-Opensource/authentication-lib'
        useGitTagForNextVersion = true
    }
}

def release(project){
    releaseProject{
        stagedProject = project
        useGitTagForNextVersion = true
        helmPush = false
        groupId = 'com.sphereon.public'
        githubOrganisation = 'Sphereon-Opensource'
        artifactIdToWatchInCentral = 'authentication-lib-main'
        artifactExtensionToWatchInCentral = 'jar'
    }
}

def mergePullRequest(prId){
    mergeAndWaitForPullRequest{
        project = 'Sphereon-Opensource/authentication-lib'
        pullRequestId = prId
    }

}
return this;