package no.fint.consumer.config;

import no.fint.consumer.utils.RestEndpoints;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import no.fint.model.felles.kodeverk.Fylke;
import no.fint.model.felles.kodeverk.iso.Kjonn;
import no.fint.model.felles.kodeverk.Kommune;
import no.fint.model.felles.kodeverk.iso.Landkode;
import no.fint.model.felles.kodeverk.iso.Sprak;
import no.fint.model.felles.kodeverk.Valuta;

public class LinkMapper {

    public static Map<String, String> linkMapper(String contextPath) {
        return ImmutableMap.<String,String>builder()
            .put(Fylke.class.getName(), contextPath + RestEndpoints.FYLKE)
            .put(Kjonn.class.getName(), contextPath + RestEndpoints.KJONN)
            .put(Kommune.class.getName(), contextPath + RestEndpoints.KOMMUNE)
            .put(Landkode.class.getName(), contextPath + RestEndpoints.LANDKODE)
            .put(Sprak.class.getName(), contextPath + RestEndpoints.SPRAK)
            .put(Valuta.class.getName(), contextPath + RestEndpoints.VALUTA)
            /* .put(TODO,TODO) */
            .build();
    }

}
