package no.fint.consumer.models.iso.kjonn;

import no.fint.model.resource.Link;
import no.fint.model.resource.felles.kodeverk.iso.KjonnResource;
import no.fint.model.resource.felles.kodeverk.iso.KjonnResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class KjonnLinker extends FintLinker<KjonnResource> {

    public KjonnLinker() {
        super(KjonnResource.class);
    }

    public void mapLinks(KjonnResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public KjonnResources toResources(Collection<KjonnResource> collection) {
        KjonnResources resources = new KjonnResources();
        collection.stream().map(this::toResource).forEach(resources::addResource);
        resources.addSelf(Link.with(self()));
        return resources;
    }

    @Override
    public String getSelfHref(KjonnResource kjonn) {
        return getAllSelfHrefs(kjonn).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(KjonnResource kjonn) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(kjonn.getSystemId()) && !isEmpty(kjonn.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(kjonn.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(KjonnResource kjonn) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(kjonn.getSystemId()) && !isEmpty(kjonn.getSystemId().getIdentifikatorverdi())) {
            builder.add(kjonn.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

