package vars

import com.gotomeeting.jirasupport.*


@NonCPS
def call(release_id) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    ReleaseRelease.releaseRelease(jiraSupport, project_key, release_id)
}


