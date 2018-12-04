/*@Grapes([
        //,
        //@Grab(group = 'com.gotomeeting.builds', module = 'jirasupport', version= '1.0.30-SNAPSHOT'),
        //@Grab(group = 'com.gotomeeting.builds', module = 'sonatypesupport', version = '1.0.1-SNAPSHOT'),
        @GrabExclude('com.sun.xml.bind:jaxb-impl'),
        @GrabExclude('com.atlassian.sal:sal-api'),
        @GrabExclude('org.apache.httpcomponents:httpcore'),
        @GrabExclude('com.octo.captcha#jcaptcha;2.0-alpha-1'),
        @GrabExclude('org.apache.httpcomponents:httpclient'),
        @GrabExclude('org.codehaus.groovy:groovy-all:2.4.11'),
        @GrabExclude('com.atlassian.jira#atlassian-jira;6.0'),
        @GrabExclude('com.atlassian.jira#atlassian-jira;6.0'),
        @GrabExclude('joda-time:joda-time:2.1'),
        @GrabExclude('com.atlassian.httpclient#atlassian-httpclient-library;1.0.0')

])*/

@Library('qw')_

import groovy.json.JsonSlurper

@NonCPS
def jenkinsBuild(String url) {
    while (true) {
        def jenkins_url = url + "buildWithParameters"
        def status_url = url + "lastBuild/api/json"
        def authString = "$ACCESS_USR:$ACCESS_PSW".getBytes().encodeBase64().toString()
        def post = new URL(jenkins_url).openConnection()
        post.setRequestMethod("POST")
        post.setRequestProperty("Authorization", "Basic ${authString}")
        post.setDoOutput(true)
        def postRC = post.getResponseCode()
        println("Status " + postRC)
        if (postRC == 401) {
            def object = post.errorStream.getText()
            println(object)
        }
        def res = null
        def get = null
        while (res == null) {
            get = new URL(status_url).openConnection()
            get.setDoOutput(true)
            get.setRequestProperty("Authorization", "Basic ${authString}")
            def getRC = get.getResponseCode()
            println("Status " + getRC)
            if (getRC == 403) {
                def object = get.errorStream.getText()
                println(object)
            }
            def jsonSlurper = new JsonSlurper()
            def object = jsonSlurper.parseText(get.getInputStream().getText())
            println(object.result)
            res = object.result
            Thread.sleep(10000)
        }
        if (!res.equals("SUCCESS")) {
            def mes = input message: "repeat?", parameters: [choice(name: "answer", choices: "skip")]
            if (mes.equals("skip")) break
        }
        else return
    }
}

/*@NonCPS
def createRelease(oldReleaseId) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    RefactorItem.refactorReleaseName(jiraSupport, oldReleaseId, "test rename")
    releaseId = CreateRelease.createRelease(jiraSupport, project_key, project_key + " NEXT RELEASE" + Math.random(), description, "2018-09-15")
    return releaseId
}

@NonCPS
def assignIssue(key) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    RefactorItem.assignIssue(jiraSupport, key, assign_name)
}*/


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
        string(name: 'test', defaultValue: 'http://localhost:8080/job/gh/')
        string(name: 'rc', defaultValue: 'http://localhost:8080/job/gh/')
        string(name: 'start_date', defaultValue: "25.08.2016")
        string(name: 'end_date', defaultValue: "25.08.2020")
        string(name: 'PACKAGE_NAME', defaultValue: 'bla')
        string(name: 'assign_name', defaultValue: 'joergv')
    }
    stages {
        stage('grab'){steps{grab()}}
        stage('Create Release Ticket') {
            steps {
                script {
                    release_key = createTask()
                }
            }
        }
        /*stage("p3") {
            parallel {*/
                stage("Perform JIRA Check for critical issues") {
                    steps {
                        script {
                            JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
                            key = createSubTask(release_key)
                            println(IssueInfo.getIssueLink(jiraSupport, key))
                            try {
                                for (String issue : CheckCriticalIssues.checkCriticalIssues(jiraSupport, jira_component_name)) {
                                    println(issue)
                                }
                            } catch (Exception e) {
                                echo e.message
                            }

                            resolveTask(key)
                        }
                    }

                }
                /*stage("Perform SonaType Checks") {
                    steps {
                        script {
                            //key = createSubTask(release_key)
                            try {
                                SonaTypeSupport sonaTypeSupport = new SonaTypeSupport(ACCESS_USR, ACCESS_PSW, SONA_URL)
                                println(sonaTypeSupport.getThreatLevel(sona_hash))
                                //resolveTask(key)
                            } catch (Exception e) {
                                echo "Very often occures an authentificatin error, wtf???"
                            }


                        }
                    }

                }*/
            //}
        //}

        /*stage("Run BAT against ED") {
            steps {
                script {
                    try {
                        key = createSubTask(release_key)
                        jenkinsBuild(test)
                        resolveTask(key)
                    } catch (Exception e) {
                        echo e
                        echo e.message
                    }


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


        stage ("p7") {
            parallel {
                stage("Deploy RC") {
                    steps {
                        script {
                            key = createSubTask(release_key)
                            echo "/opt/ibmrationalsoftware/bftrigger.pl MeetingService_OS_-_ED true MeetingServiceOSBase ${PACKAGE_NAME}"
                            echo "${PACKAGE_NAME}"
                            shell '''sed -r '/environment->getEntry/ s/"((Rollback)?Package)"/"ED\1"/' /opt/ibmrationalsoftware/bftrigger.pl > mybftrigger.pl'''
                            shell """/usr/bin/perl ./mybftrigger.pl "ScreenSharingService_OS_-_ED" true "ScreenSharingServiceOSBase" ${PACKAGE_NAME}"""
                            resolveTask(key)
                        }
                    }

                }
                stage("Create JIRA release version") {
                    steps {
                        script {
                            key = createSubTask(release_key)
                            release_id = createRelease("39350")
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
                    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
                    key = createSubTask(release_key)


                    CreateIssue.createDeployment(jiraSupport, "WDO", "Deploy $jira_component_name to stage", description, new Date(), new Date(),
                            IssueInfo.getIssueLink(jiraSupport, project_key), "Live")
                    CreateIssue.createDeployment(jiraSupport, "WDO", "Deploy $jira_component_name to stage", description, new Date(), new Date(),
                            IssueInfo.getIssueLink(jiraSupport, project_key), "Stage")
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
                    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
                    ReleaseRelease.releaseRelease(jiraSupport, project_key, "39357")
                      resolveTask(key)
                }
            }

        }*/
    }
}

