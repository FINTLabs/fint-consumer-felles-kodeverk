package no.fint.consumer.models.iso;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class IsoController {

    @RequestMapping("/iso/**")
    public ModelAndView redirectIso(HttpServletRequest request) {
        final String viewName = "forward:" + StringUtils.substringAfter(request.getRequestURI(), "/iso");
        log.info(viewName);
        return new ModelAndView(viewName);
    }
}
