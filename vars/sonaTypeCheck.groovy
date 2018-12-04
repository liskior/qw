package vars

import com.gotomeeting.sonatype.*

def call() {
    try {
        SonaTypeSupport sonaTypeSupport = new SonaTypeSupport(ACCESS_USR, ACCESS_PSW, SONA_URL)
        println(sonaTypeSupport.getThreatLevel(sona_hash))
    } catch (Exception e) {
        e.message
    }

}
