package no.fint.consumer.model

import no.fint.consumer.models.iso.kjonn.KjonnCacheService
import no.fint.model.resource.felles.kodeverk.iso.KjonnResource
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles('test')
class IsoPathSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    KjonnCacheService cacheService = Mock()

    def 'Able to retrieve Kjonn'() {

        when:
        def response = mockMvc.perform(
                get('/kjonn/systemid/1')
                        .header('x-org-id', 'test.org')
                        .header('x-client', 'Spock')
        )

        then:
        1 * cacheService.getKjonnBySystemId('test.org', '1') >> Optional.of(new KjonnResource(
                navn: 'Mann'
        ))
        response.andExpect(status().is2xxSuccessful()).andExpect(jsonPath('$.navn').value('Mann'))
    }

    def 'Able to retrieve ISO Kjonn'() {

        when:
        def response = mockMvc.perform(
                get('/iso/kjonn/systemid/1')
                        .header('x-org-id', 'test.org')
                        .header('x-client', 'Spock')
        )

        then:
        response.andExpect(status().is2xxSuccessful()).andExpect(forwardedUrl('/kjonn/systemid/1'))
    }
}
