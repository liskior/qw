package vars

import com.gotomeeting.jirasupport.*


@NonCPS
def call() {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    ReleaseRelease.releaseRelease(jiraSupport, project_key, "39357")
}


