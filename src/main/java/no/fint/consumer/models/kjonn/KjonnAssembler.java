package no.fint.consumer.models.kjonn;

import no.fint.model.felles.kodeverk.iso.Kjonn;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class KjonnAssembler extends FintResourceAssembler<Kjonn> {

    public KjonnAssembler() {
        super(KjonnController.class);
    }

    @Override
    public FintResourceSupport assemble(Kjonn kjonn , FintResource<Kjonn> fintResource) {
        return createResourceWithId(kjonn.get***fixme***().getIdentifikatorverdi(), fintResource, "***fixme***");
    }
}

