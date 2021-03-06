package no.fint.consumer.models.kommune;

import no.fint.model.resource.felles.kodeverk.KommuneResource;
import no.fint.model.resource.felles.kodeverk.KommuneResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class KommuneLinker extends FintLinker<KommuneResource> {

    public KommuneLinker() {
        super(KommuneResource.class);
    }

    public void mapLinks(KommuneResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KommuneResources toResources(Collection<KommuneResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public KommuneResources toResources(Stream<KommuneResource> stream, int offset, int size, int totalItems) {
        KommuneResources resources = new KommuneResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(KommuneResource kommune) {
        return getAllSelfHrefs(kommune).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(KommuneResource kommune) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(kommune.getSystemId()) && !isEmpty(kommune.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(kommune.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(KommuneResource kommune) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(kommune.getSystemId()) && !isEmpty(kommune.getSystemId().getIdentifikatorverdi())) {
            builder.add(kommune.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

