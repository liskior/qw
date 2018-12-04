package vars

import com.gotomeeting.jirasupport.*

@NonCPS
def call(oldReleaseId) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    try {
        RefactorItem.refactorReleaseName(jiraSupport, oldReleaseId, "test rename")
    } catch (Exception e) {
        echo e.message
    }
    releaseId = CreateRelease.createRelease(jiraSupport, project_key, project_key + " NEXT RELEASE" + Math.random(), description, "2018-09-15")
    echo ReleaseInfo.getReleaseLink(jiraSupport, releaseId, project_key)
    return releaseId


}
