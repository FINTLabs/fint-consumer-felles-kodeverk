package no.fint.consumer.models.fylke;

import no.fint.model.resource.felles.kodeverk.FylkeResource;
import no.fint.model.resource.felles.kodeverk.FylkeResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public FylkeResources toResources(Stream<FylkeResource> stream, int offset, int size, int totalItems) {
        FylkeResources resources = new FylkeResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(FylkeResource fylke) {
        return getAllSelfHrefs(fylke).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(FylkeResource fylke) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(fylke.getSystemId()) && !isEmpty(fylke.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(fylke.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(FylkeResource fylke) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(fylke.getSystemId()) && !isEmpty(fylke.getSystemId().getIdentifikatorverdi())) {
            builder.add(fylke.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

