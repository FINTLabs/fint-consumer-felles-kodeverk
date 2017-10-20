package no.fint.consumer.models.fylke;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import no.fint.cache.CacheService;
import no.fint.cache.FintCache;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.model.relation.FintResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import no.fint.model.felles.kodeverk.Fylke;
import no.fint.model.felles.kodeverk.KodeverkActions;

@Slf4j
@Service
public class FylkeCacheService extends CacheService<FintResource<Fylke>> {

    public static final String MODEL = Fylke.class.getSimpleName().toLowerCase();

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    public FylkeCacheService() {
        super(MODEL, KodeverkActions.GET_ALL_FYLKE);
    }

    @PostConstruct
    public void init() {
        Arrays.stream(props.getOrgs()).forEach(orgId -> {
            FintCache<FintResource<Fylke>> cache = new FintCache<>();
            put(orgId, cache);
        });
    }

    @Scheduled(initialDelayString = ConsumerProps.CACHE_INITIALDELAY_FYLKE, fixedRateString = ConsumerProps.CACHE_FIXEDRATE_FYLKE)
    public void populateCacheAll() {
        Arrays.stream(props.getOrgs()).forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    private void populateCache(String orgId) {
		log.info("Populating Fylke cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_ALL_FYLKE, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }

    public Optional<FintResource<Fylke>> getFylke(String orgId, String SystemId) {
        return getOne(orgId, (fintResource) -> fintResource.getResource().getSystemId().getIdentifikatorverdi().equals(SystemId));
    }

	@Override
    public void onAction(Event event) {
        update(event, new TypeReference<List<FintResource<Fylke>>>() {
        });
    }
}
