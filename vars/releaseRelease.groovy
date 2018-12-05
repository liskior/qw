package vars

import com.gotomeeting.jirasupport.*


@NonCPS
def call(release_id) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    try {
        ReleaseRelease.releaseRelease(jiraSupport, project_key, release_id.toString())
        echo "Release released successful"
    } catch (Exception e) {
        echo e.message
    }

}


