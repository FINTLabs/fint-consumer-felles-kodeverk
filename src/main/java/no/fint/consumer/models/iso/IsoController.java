package no.fint.consumer.models.iso;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IsoController {

    @RequestMapping("/iso/**")
    public ModelAndView redirectIso(HttpServletRequest request) {
        return new ModelAndView("forward:" + request.getRequestURI().replace("/iso", ""));
    }
}
