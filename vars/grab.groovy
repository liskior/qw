@GrabResolver(name = 'com.gotomeeting.builds', root = 'https://artifactory.prodwest.citrixsaassbe.net/artifactory/libs-snapshot-local/')
@Grab(group='com.gotomeeting.builds', module='jirasupport', version='1.0.35-SNAPSHOT')
@Grab(group='com.gotomeeting.builds', module='sonatypesupport', version='1.0.1-SNAPSHOT')
import com.gotomeeting.sonatypesupport.*
import com.gotomeeting.jirasupport.*

@NonCPS
def call() {
}
