package no.fint.consumer.service

import no.fint.audit.FintAuditService
import no.fint.consumer.event.EventListener
import no.fint.consumer.status.StatusCache
import no.fint.event.model.Event
import spock.lang.Specification

class EventListenerSpec extends Specification {
    private EventListener eventListener

    void setup() {
        eventListener = new EventListener(
                cacheServices: [],
                statusCache: Mock(StatusCache),
                fintAuditService: Mock(FintAuditService))
    }

    def "No exception is thrown when receiving event"() {
        when:
        eventListener.accept(new Event(corrId: '123'))

        then:
        noExceptionThrown()
    }
}
