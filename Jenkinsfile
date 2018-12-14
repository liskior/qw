@Library('qw')_

import groovy.json.JsonSlurper

@NonCPS
def jenkinsBuild(String url) {
    while (true) {
        def jenkins_url = url + "build"
        def status_url = url + "lastBuild/api/json"
        def authString = "$ACCESS_USR:$ACCESS_PSW".getBytes().encodeBase64().toString()

        def post = new URL(jenkins_url).openConnection()
        post.setRequestMethod("POST")

        post.setDoOutput(true)
        def postRC = post.getResponseCode()
        println("Status " + postRC)
        if (postRC != 200 && postRC != 201) {
            post.setRequestProperty("Authorization", "Basic ${authString}")
            postRC = post.getResponseCode()
            if (postRC == 200 || postRC != 201) break
            def object = post.errorStream.getText()
            println(object)
        }
        def res = null
        def get = null
        while (res == null) {
            get = new URL(status_url).openConnection()
            get.setDoOutput(true)
            //get.setRequestProperty("Authorization", "Basic ${authString}")
            def getRC = get.getResponseCode()
            println("Status " + getRC)
            if (getRC != 200) {
                def object = get.errorStream.getText()
                println(object)
                continue
            }
            def jsonSlurper = new JsonSlurper()
            def object = jsonSlurper.parseText(get.getInputStream().getText())
            println(object.result)
            res = object.result
            Thread.sleep(10000)
        }
        if (!res.equals("SUCCESS")) {
            def mes = input message: "repeat?", parameters: [choice(name: "answer", choices: "skip\nrepeat")]
            if (mes.equals("skip")) break
        }
        else break
    }
}


pipeline {
    agent any
    /*agent {
        label 'rhel6'
    }*/
    environment {
        ACCESS = credentials('akryzhanovskaya')
        JIRA_URL = "https://devjira.ops.expertcity.com/"
        SONA_URL = "https://nexus.getgotools.net"
    }
    parameters {
        string(name: 'jira_component_name', defaultValue: 'usage-service')
        string(name: 'version', defaultValue: '1.0.0')
        string(name: 'description', defaultValue: 'Very interesting description.')
        string(name: 'project_key', defaultValue: 'GTMTWO')
        string(name: 'sonar_project_name', defaultValue: 'g2m-ios-app:development')
        string(name: 'sona_hash', defaultValue: "de459ce00e445a4c4d87")
        string(name: 'test', defaultValue: 'https://jenkinsstage.prodeast.citrixsaassbe.net/jenkins/job/Communication_Cloud/job/GoToMeeting/job/test/')
        string(name: 'rc', defaultValue: 'https://jenkinsstage.prodeast.citrixsaassbe.net/jenkins/job/Communication_Cloud/job/GoToMeeting/job/test/')
        string(name: 'start_date', defaultValue: "25.08.2016")
        string(name: 'end_date', defaultValue: "25.08.2020")
        string(name: 'PACKAGE_NAME', defaultValue: 'bla')
        string(name: 'assign_name', defaultValue: 'joergv')
        string(name: 'old_release_id', defaultValue: '40242')
    }
    stages {
        stage('grab') {
            steps {
                grab()
            }
        }
        stage('Create Release Ticket') {
            steps {
                script {
                    release_key = createTask()
                }
            }
        }
        stage("p3") {
            parallel {
                stage("Perform JIRA Check for critical issues") {
                    steps {
                        script {
                            key = createSubTask(release_key)
                            checkCritical()
                            resolveTask(key)
                        }
                    }

                }
                stage("Perform SonaType Checks") {
                    steps {
                        script {
                            key = createSubTask(release_key)
                            sonaTypeCheck()
                            resolveTask(key)
                        }
                    }

                }
            }
        }

        stage("Run BAT against ED") {
            steps {
                script {
                    key = createSubTask(release_key)
                    jenkinsBuild(test)
                    resolveTask(key)
                }
            }
        }


        stage('Create Release Candidate') {
            steps {
                script {
                    key = createSubTask(release_key)
                    jenkinsBuild(rc)
                    resolveTask(key)
                }

            }

        }


        stage("p7") {
            parallel {
                stage("Deploy RC") {
                    steps {
                        script {
                            key = createSubTask(release_key)
                            echo "/opt/ibmrationalsoftware/bftrigger.pl MeetingService_OS_-_ED true MeetingServiceOSBase ${PACKAGE_NAME}"
                            echo "${PACKAGE_NAME}"
                            shell '''sed -r '/environment->getEntry/ s/"((Rollback)?Package)"/"ED\1"/' /opt/ibmrationalsoftware/bftrigger.pl > mybftrigger.pl'''
                            shell """/usr/bin/perl ./mybftrigger.pl "ScreenSharingService_OS_-_ED" true "ScreenSharingServiceOSBase" ${
                                PACKAGE_NAME
                            }"""
                            resolveTask(key)
                        }
                    }

                }
                stage("Create JIRA release version") {
                    steps {
                        script {
                            key = createSubTask(release_key)
                            release_id = createRelease(old_release_id)
                            resolveTask(key)

                        }
                    }

                }
            }
        }
        stage("p2") {
            parallel {
                stage("Run BAT against RC") {
                    steps {
                        script {
                            key = createSubTask(release_key)
                            jenkinsBuild(rc)
                            resolveTask(key)
                        }
                    }

                }
            }
        }


        stage("Create OPS deployment ticket") {
            steps {
                script {
                    key = createSubTask(release_key)
                    createDeployment()
                    resolveTask(key)
                }
            }

        }
        stage("p1") {
            parallel {
                stage("Request PO approval") {
                    steps {
                        script {
                            key = createSubTask(release_key)
                            assignIssue(key)
                        }
                    }

                }
                stage("Request QE approval") {
                    steps {
                        script {
                            key = createSubTask(release_key)
                            assignIssue(key)

                        }
                    }

                }

            }
        }

        stage("Make JIRA version as released") {
            steps {
                script {
                    key = createSubTask(release_key)
                    releaseRelease(release_id)
                    resolveTask(key)
                }
            }

        }
    }

}