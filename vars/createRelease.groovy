import com.gotomeeting.jirasupport.*

@NonCPS
def call(oldReleaseId) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    try {
        RefactorItem.refactorReleaseName(jiraSupport, oldReleaseId, "test rename")
        echo "Old Release renamed successfull"
    } catch (Exception e) {
        echo e.message
    }
    releaseId = CreateRelease.createRelease(jiraSupport, project_key, project_key + " NEXT RELEASE" + Math.random(), description, "2018-09-15")
    echo "New Release:\n" + ReleaseInfo.getReleaseLink(jiraSupport, releaseId.toString(), project_key)
    return releaseId


}
