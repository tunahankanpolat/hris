package obss.hris.controller;

import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.model.response.GetHumanResourceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/hr/v1/")
public class HumanResourceController {
    private HumanResourceService humanResourceService;

    @Autowired
    public HumanResourceController(HumanResourceService humanResourceService) {
        this.humanResourceService = humanResourceService;
    }

    @GetMapping("me")
    public GetHumanResourceResponse getHumanResource(Principal principal) {
        return humanResourceService.getByUsername(principal.getName());
    }
}
