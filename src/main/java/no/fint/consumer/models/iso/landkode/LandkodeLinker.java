package no.fint.consumer.models.iso.landkode;

import no.fint.model.resource.Link;
import no.fint.model.resource.felles.kodeverk.iso.LandkodeResource;
import no.fint.model.resource.felles.kodeverk.iso.LandkodeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;


@Component
public class LandkodeLinker extends FintLinker<LandkodeResource> {

    public LandkodeLinker() {
        super(LandkodeResource.class);
    }

    public void mapLinks(LandkodeResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public LandkodeResources toResources(Collection<LandkodeResource> collection) {
        LandkodeResources resources = new LandkodeResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(LandkodeResource landkode) {
        if (!isNull(landkode.getSystemId()) && !isEmpty(landkode.getSystemId().getIdentifikatorverdi())) {
            return createHrefWithId(landkode.getSystemId().getIdentifikatorverdi(), "systemid");
        }
        
        return null;
    }
    
}

