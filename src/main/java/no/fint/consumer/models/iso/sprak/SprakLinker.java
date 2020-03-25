package no.fint.consumer.models.iso.sprak;

import no.fint.model.resource.Link;
import no.fint.model.resource.felles.kodeverk.iso.SprakResource;
import no.fint.model.resource.felles.kodeverk.iso.SprakResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class SprakLinker extends FintLinker<SprakResource> {

    public SprakLinker() {
        super(SprakResource.class);
    }

    public void mapLinks(SprakResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public SprakResources toResources(Collection<SprakResource> collection) {
        SprakResources resources = new SprakResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(SprakResource sprak) {
        return getAllSelfHrefs(sprak).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(SprakResource sprak) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(sprak.getSystemId()) && !isEmpty(sprak.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(sprak.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(SprakResource sprak) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(sprak.getSystemId()) && !isEmpty(sprak.getSystemId().getIdentifikatorverdi())) {
            builder.add(sprak.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

