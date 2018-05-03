package org.frenchu.repograbber.dto

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class RepositoryDetailsRSTest extends Specification {

    def 'should fullfil contarct between equals and hashCode'() {
        expect:
        EqualsVerifier.forClass(RepositoryDetailsRS).verify()
    }
}
