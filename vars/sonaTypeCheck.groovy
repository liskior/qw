package vars

import com.gotomeeting.sonatype.*

def call() {
    SonaTypeSupport sonaTypeSupport = new SonaTypeSupport(ACCESS_USR, ACCESS_PSW, SONA_URL)
    println(sonaTypeSupport.getThreatLevel(sona_hash))
}
