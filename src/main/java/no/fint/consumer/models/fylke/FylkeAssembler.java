package no.fint.consumer.models.fylke;

import no.fint.model.felles.kodeverk.Fylke;
import no.fint.model.relation.FintResource;
import no.fint.relations.FintResourceAssembler;
import no.fint.relations.FintResourceSupport;
import org.springframework.stereotype.Component;

@Component
public class FylkeAssembler extends FintResourceAssembler<Fylke> {

    public FylkeAssembler() {
        super(FylkeController.class);
    }

    @Override
    public FintResourceSupport assemble(Fylke fylke , FintResource<Fylke> fintResource) {
        return createResourceWithId(fylke.getSystemId().getIdentifikatorverdi(), fintResource, "systemId");
    }
}

