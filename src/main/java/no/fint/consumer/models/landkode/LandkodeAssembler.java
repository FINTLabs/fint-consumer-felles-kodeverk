package no.fint.consumer.models.landkode;

import no.fint.model.felles.kodeverk.iso.Landkode;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class LandkodeAssembler extends FintResourceAssembler<Landkode> {

    public LandkodeAssembler() {
        super(LandkodeController.class);
    }

    @Override
    public FintResourceSupport assemble(Landkode landkode , FintResource<Landkode> fintResource) {
        return createResourceWithId(landkode.get***fixme***().getIdentifikatorverdi(), fintResource, "***fixme***");
    }
}

