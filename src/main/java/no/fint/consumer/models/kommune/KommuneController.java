package no.fint.consumer.models.kommune;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import no.fint.audit.FintAuditService;
import no.fint.consumer.config.Constants;
import no.fint.consumer.utils.RestEndpoints;
import no.fint.event.model.Event;
import no.fint.event.model.HeaderConstants;
import no.fint.event.model.Status;

import no.fint.model.relation.FintResource;
import no.fint.relations.FintRelationsMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import no.fint.model.felles.kodeverk.Kommune;
import no.fint.model.felles.kodeverk.KodeverkActions;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = RestEndpoints.KOMMUNE, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class KommuneController {

    @Autowired
    private KommuneCacheService cacheService;

    @Autowired
    private FintAuditService fintAuditService;

    @Autowired
    private KommuneAssembler assembler;

    @GetMapping("/last-updated")
    public Map<String, String> getLastUpdated(@RequestHeader(HeaderConstants.ORG_ID) String orgId) {
        String lastUpdated = Long.toString(cacheService.getLastUpdated(orgId));
        return ImmutableMap.of("lastUpdated", lastUpdated);
    }

    @GetMapping("/cache/size")
     public ImmutableMap<String, Integer> getCacheSize(@RequestHeader(HeaderConstants.ORG_ID) String orgId) {
        return ImmutableMap.of("size", cacheService.getAll(orgId).size());
    }

    @PostMapping("/cache/rebuild")
    public void rebuildCache(@RequestHeader(HeaderConstants.ORG_ID) String orgId) {
        cacheService.rebuildCache(orgId);
    }

    @GetMapping
    public ResponseEntity getKommune(@RequestParam(required = false) Long sinceTimeStamp) {
        log.info("SinceTimeStamp: {}", sinceTimeStamp);

        Event event = new Event(Constants.ORG_ID, Constants.COMPONENT, KodeverkActions.GET_ALL_KOMMUNE, Constants.ORG_ID);
        fintAuditService.audit(event);

        fintAuditService.audit(event, Status.CACHE);

        List<FintResource<Kommune>> kommune;
        if (sinceTimeStamp == null) {
            kommune = cacheService.getAll(Constants.ORG_ID);
        } else {
            kommune = cacheService.getAll(Constants.ORG_ID, sinceTimeStamp);
        }

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        return assembler.resources(kommune);
    }

    @GetMapping("/systemId/{id}")
    public ResponseEntity getKommune(@PathVariable String id) {

        Event event = new Event(Constants.ORG_ID, Constants.COMPONENT, KodeverkActions.GET_KOMMUNE, Constants.ORG_ID);
        fintAuditService.audit(event);

        fintAuditService.audit(event, Status.CACHE);

        Optional<FintResource<Kommune>> kommune = cacheService.getKommune(Constants.ORG_ID, id);

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        if (kommune.isPresent()) {
            return assembler.resource(kommune.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * TODO: Add endpoints for all Identifikatore.
     */

}

