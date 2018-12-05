import com.gotomeeting.jirasupport.*

@NonCPS
def call(key) {
    JiraSupport jiraSupport = new JiraSupport(ACCESS_USR, ACCESS_PSW, JIRA_URL)
    try {
        DoStatusTransition.doStatusTransition(jiraSupport, key, TransitionStatus.START_PROGRESS)
        echo "Task status updated"
    } catch (Exception e) {
        echo e.message
    }
}
