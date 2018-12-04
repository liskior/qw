package vars

@GrabResolver(name = 'com.gotomeeting.builds', root = 'https://artifactory.prodwest.citrixsaassbe.net/artifactory/libs-snapshot-local/')
@Grab(group='com.gotomeeting.builds', module='jirasupport', version='1.0.30-SNAPSHOT')
import com.gotomeeting.jirasupport.JiraSupport


@NonCPS
def createTask() {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    release_key = CreateIssue.createIssue(jiraSupport, project_key, project_key + version, description)
    println(IssueInfo.getIssueLink(jiraSupport, release_key))
    return release_key
}
