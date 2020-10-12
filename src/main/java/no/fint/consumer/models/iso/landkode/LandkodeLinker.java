package no.fint.consumer.models.iso.landkode;

import no.fint.model.resource.felles.kodeverk.iso.LandkodeResource;
import no.fint.model.resource.felles.kodeverk.iso.LandkodeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public LandkodeResources toResources(Stream<LandkodeResource> stream, int offset, int size, int totalItems) {
        LandkodeResources resources = new LandkodeResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(LandkodeResource landkode) {
        return getAllSelfHrefs(landkode).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(LandkodeResource landkode) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(landkode.getSystemId()) && !isEmpty(landkode.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(landkode.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(LandkodeResource landkode) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(landkode.getSystemId()) && !isEmpty(landkode.getSystemId().getIdentifikatorverdi())) {
            builder.add(landkode.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

