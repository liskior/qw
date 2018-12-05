import com.gotomeeting.sonatypesupport.*

def call() {
    try {
        SonaTypeSupport sonaTypeSupport = new SonaTypeSupport(ACCESS_USR, ACCESS_PSW, SONA_URL)
        echo "SonaType theat level: " + sonaTypeSupport.getThreatLevel(sona_hash)
    } catch (Exception e) {
        echo e.message
    }

}
