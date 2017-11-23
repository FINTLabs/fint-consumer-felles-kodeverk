package no.fint.consumer.models.fylke;

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

import no.fint.model.felles.kodeverk.Fylke;
import no.fint.model.felles.kodeverk.KodeverkActions;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = RestEndpoints.FYLKE, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class FylkeController {

    @Autowired
    private FylkeCacheService cacheService;

    @Autowired
    private FintAuditService fintAuditService;

    @Autowired
    private FylkeAssembler assembler;

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
    public ResponseEntity getFylke(@RequestParam(required = false) Long sinceTimeStamp) {
        log.info("SinceTimeStamp: {}", sinceTimeStamp);

        Event event = new Event(Constants.ORG_ID, Constants.COMPONENT, KodeverkActions.GET_ALL_FYLKE, Constants.ORG_ID);
        fintAuditService.audit(event);

        fintAuditService.audit(event, Status.CACHE);

        List<FintResource<Fylke>> fylke;
        if (sinceTimeStamp == null) {
            fylke = cacheService.getAll(Constants.ORG_ID);
        } else {
            fylke = cacheService.getAll(Constants.ORG_ID, sinceTimeStamp);
        }

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        return assembler.resources(fylke);
    }

    @GetMapping("/systemId/{id}")
    public ResponseEntity getFylke(@PathVariable String id) {
        Event event = new Event(Constants.ORG_ID, Constants.COMPONENT, KodeverkActions.GET_FYLKE, Constants.ORG_ID);
        fintAuditService.audit(event);

        fintAuditService.audit(event, Status.CACHE);

        Optional<FintResource<Fylke>> fylke = cacheService.getFylke(Constants.ORG_ID, id);

        fintAuditService.audit(event, Status.CACHE_RESPONSE, Status.SENT_TO_CLIENT);

        if (fylke.isPresent()) {
            return assembler.resource(fylke.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * TODO: Add endpoints for all Identifikatore.
     */

}

