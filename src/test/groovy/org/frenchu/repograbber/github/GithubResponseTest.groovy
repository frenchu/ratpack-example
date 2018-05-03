package org.frenchu.repograbber.github

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class GithubResponseTest extends Specification {

    def 'should fullfil contarct between equals and hashCode'() {
        expect:
        EqualsVerifier.forClass(GithubResponse).verify()
    }
}
