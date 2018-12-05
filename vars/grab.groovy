package vars

@GrabResolver(name = 'com.gotomeeting.builds', root = 'https://artifactory.prodwest.citrixsaassbe.net/artifactory/libs-snapshot-local/')
//@Grab(group='com.gotomeeting.builds', module='jirasupport', version='1.0.32-SNAPSHOT')
@Grab(group='com.gotomeeting.builds', module='sonatypesupport', version='1.0.1-SNAPSHOT')
import com.gotomeeting.sonatypesupport.SonaTypeSupport
import com.gotomeeting.jirasupport.*

@NonCPS
def call() {
    //JiraSupport jiraSupport = new JiraSupport("qw", "qw", "qw")
    echo "Jira"
    SonaTypeSupport sonaTypeSupport = new SonaTypeSupport("qw", "qw", "qw")
    echo "libraries are imported successfully"
}
