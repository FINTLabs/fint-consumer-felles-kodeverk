package no.fint.consumer.models.valuta;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

import no.fint.cache.CacheService;
import no.fint.cache.model.CacheObject;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.relations.FintResourceCompatibility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import no.fint.model.felles.kodeverk.Valuta;
import no.fint.model.resource.felles.kodeverk.ValutaResource;
import no.fint.model.felles.kodeverk.KodeverkActions;
import no.fint.model.felles.kompleksedatatyper.Identifikator;

@Slf4j
@Service
@ConditionalOnProperty(name = "fint.consumer.cache.disabled.valuta", havingValue = "false", matchIfMissing = true)
public class ValutaCacheService extends CacheService<ValutaResource> {

    public static final String MODEL = Valuta.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private ValutaLinker linker;

    private JavaType javaType;

    private ObjectMapper objectMapper;

    public ValutaCacheService() {
        super(MODEL, KodeverkActions.GET_ALL_VALUTA, KodeverkActions.UPDATE_VALUTA);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, ValutaResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        props.getAssets().forEach(this::createCache);
    }

    @Scheduled(initialDelayString = Constants.CACHE_INITIALDELAY_VALUTA, fixedRateString = Constants.CACHE_FIXEDRATE_VALUTA)
    public void populateCacheAll() {
        props.getAssets().forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    @Override
    public void populateCache(String orgId) {
		log.info("Populating Valuta cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, KodeverkActions.GET_ALL_VALUTA, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<ValutaResource> getValutaByBokstavkode(String orgId, String bokstavkode) {
        return getOne(orgId, bokstavkode.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(ValutaResource::getBokstavkode)
                .map(Identifikator::getIdentifikatorverdi)
                .map(bokstavkode::equals)
                .orElse(false));
    }

    public Optional<ValutaResource> getValutaByNummerkode(String orgId, String nummerkode) {
        return getOne(orgId, nummerkode.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(ValutaResource::getNummerkode)
                .map(Identifikator::getIdentifikatorverdi)
                .map(nummerkode::equals)
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<ValutaResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<ValutaResource> to ValutaResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), ValutaResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(linker::mapLinks);
        if (KodeverkActions.valueOf(event.getAction()) == KodeverkActions.UPDATE_VALUTA) {
            if (event.getResponseStatus() == ResponseStatus.ACCEPTED || event.getResponseStatus() == ResponseStatus.CONFLICT) {
                List<CacheObject<ValutaResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
                addCache(event.getOrgId(), cacheObjects);
                log.info("Added {} cache objects to cache for {}", cacheObjects.size(), event.getOrgId());
            } else {
                log.debug("Ignoring payload for {} with response status {}", event.getOrgId(), event.getResponseStatus());
            }
        } else {
            List<CacheObject<ValutaResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
            updateCache(event.getOrgId(), cacheObjects);
            log.info("Updated cache for {} with {} cache objects", event.getOrgId(), cacheObjects.size());
        }
    }
}
