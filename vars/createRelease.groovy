package vars

import com.gotomeeting.jirasupport.*

@NonCPS
def call(oldReleaseId) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    RefactorItem.refactorReleaseName(jiraSupport, oldReleaseId, "test rename")
    releaseId = CreateRelease.createRelease(jiraSupport, project_key, project_key + " NEXT RELEASE" + Math.random(), description, "2018-09-15")
    return releaseId
}
