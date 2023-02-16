package no.fint.consumer.models.valuta;

import no.fint.model.resource.felles.kodeverk.ValutaResource;
import no.fint.model.resource.felles.kodeverk.ValutaResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ValutaLinker extends FintLinker<ValutaResource> {

    public ValutaLinker() {
        super(ValutaResource.class);
    }

    public void mapLinks(ValutaResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ValutaResources toResources(Collection<ValutaResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public ValutaResources toResources(Stream<ValutaResource> stream, int offset, int size, int totalItems) {
        ValutaResources resources = new ValutaResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(ValutaResource valuta) {
        return getAllSelfHrefs(valuta).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(ValutaResource valuta) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(valuta.getBokstavkode()) && !isEmpty(valuta.getBokstavkode().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(valuta.getBokstavkode().getIdentifikatorverdi(), "bokstavkode"));
        }
        if (!isNull(valuta.getNummerkode()) && !isEmpty(valuta.getNummerkode().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(valuta.getNummerkode().getIdentifikatorverdi(), "nummerkode"));
        }
        
        return builder.build();
    }

    int[] hashCodes(ValutaResource valuta) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(valuta.getBokstavkode()) && !isEmpty(valuta.getBokstavkode().getIdentifikatorverdi())) {
            builder.add(valuta.getBokstavkode().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(valuta.getNummerkode()) && !isEmpty(valuta.getNummerkode().getIdentifikatorverdi())) {
            builder.add(valuta.getNummerkode().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

