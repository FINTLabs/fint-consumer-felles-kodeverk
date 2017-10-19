package no.fint.consumer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ConsumerProps {


	public static final String CACHE_INITIALDELAY_FYLKE = "${fint.consumer.cache.initialDelay.fylke:60000}";
	public static final String CACHE_FIXEDRATE_FYLKE = "${fint.consumer.cache.fixedRate.fylke:900000}";

	public static final String CACHE_INITIALDELAY_KOMMUNE = "${fint.consumer.cache.initialDelay.kommune:70000}";
	public static final String CACHE_FIXEDRATE_KOMMUNE = "${fint.consumer.cache.fixedRate.kommune:900000}";



        @Value("${fint.events.orgIds:fint.no}")
        private String[] orgs;

}
