package no.fint.consumer.models.fylke;

import no.fint.model.resource.Link;
import no.fint.model.resource.felles.kodeverk.FylkeResource;
import no.fint.model.resource.felles.kodeverk.FylkeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class FylkeLinker extends FintLinker<FylkeResource> {

    public FylkeLinker() {
        super(FylkeResource.class);
    }

    public void mapLinks(FylkeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public FylkeResources toResources(Collection<FylkeResource> collection) {
        FylkeResources resources = new FylkeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(FylkeResource fylke) {
        if (!isNull(fylke.getSystemId()) && !isEmpty(fylke.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(fylke.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

