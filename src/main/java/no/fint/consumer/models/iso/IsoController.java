package no.fint.consumer.models.iso;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Slf4j
public class IsoController {

    @RequestMapping("/iso/**")
    public ModelAndView redirectIso() {
        final String viewName = "forward:" + ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString().replace("/iso/", "/");
        log.info(viewName);
        return new ModelAndView(viewName);
    }
}
