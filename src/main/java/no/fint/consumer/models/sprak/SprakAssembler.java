package no.fint.consumer.models.sprak;

import no.fint.model.felles.kodeverk.iso.Sprak;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class SprakAssembler extends FintResourceAssembler<Sprak> {

    public SprakAssembler() {
        super(SprakController.class);
    }

    @Override
    public FintResourceSupport assemble(Sprak sprak , FintResource<Sprak> fintResource) {
        return createResourceWithId(sprak.getSystemId().getIdentifikatorverdi(), fintResource, "SystemId");
    }
}

