package vars

@GrabResolver(name = 'com.gotomeeting.builds', root = 'https://artifactory.prodwest.citrixsaassbe.net/artifactory/libs-snapshot-local/')
@Grab(group='com.gotomeeting.builds', module='jirasupport', version='1.0.32-SNAPSHOT')
@Grab(group='com.gotomeeting.builds', module='sonatypesupport', version='1.0.1-SNAPSHOT')

def call() {}
