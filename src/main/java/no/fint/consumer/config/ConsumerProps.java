package no.fint.consumer.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ConsumerProps {

	public static final String CACHE_FIXEDRATE_FYLKE = "${fint.consumer.cache.fixedRate.fylke:900000}";
	public static final String CACHE_FIXEDRATE_KJONN = "${fint.consumer.cache.fixedRate.kjonn:900000}";
	public static final String CACHE_FIXEDRATE_KOMMUNE = "${fint.consumer.cache.fixedRate.kommune:900000}";
	public static final String CACHE_FIXEDRATE_LANDKODE = "${fint.consumer.cache.fixedRate.landkode:900000}";
	public static final String CACHE_FIXEDRATE_SPRAK = "${fint.consumer.cache.fixedRate.sprak:900000}";
	public static final String CACHE_INITIALDELAY_FYLKE = "${fint.consumer.cache.initialDelay.fylke:50000}";
	public static final String CACHE_INITIALDELAY_KJONN = "${fint.consumer.cache.initialDelay.kjonn:60000}";
	public static final String CACHE_INITIALDELAY_KOMMUNE = "${fint.consumer.cache.initialDelay.kommune:70000}";
	public static final String CACHE_INITIALDELAY_LANDKODE = "${fint.consumer.cache.initialDelay.landkode:80000}";
	public static final String CACHE_INITIALDELAY_SPRAK = "${fint.consumer.cache.initialDelay.sprak:90000}";


    private String[] orgs = { "fint.health", Constants.ORG_ID };

}
