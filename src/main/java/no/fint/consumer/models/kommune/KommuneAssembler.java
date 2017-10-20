package no.fint.consumer.models.kommune;

import no.fint.model.felles.kodeverk.Kommune;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class KommuneAssembler extends FintResourceAssembler<Kommune> {

    public KommuneAssembler() {
        super(KommuneController.class);
    }

    @Override
    public FintResourceSupport assemble(Kommune kommune , FintResource<Kommune> fintResource) {
        return createResourceWithId(kommune.getSystemId().getIdentifikatorverdi(), fintResource, "SystemId");
    }
}

