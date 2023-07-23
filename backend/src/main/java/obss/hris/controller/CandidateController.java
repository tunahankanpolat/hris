package obss.hris.controller;

import obss.hris.business.abstracts.CandidateService;
import obss.hris.model.response.GetCandidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/candidate/v1/")
public class CandidateController {
    private CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("me")
    public GetCandidateResponse getCandidate(@AuthenticationPrincipal OAuth2User oauth2User) {
        return candidateService.getCandidate(oauth2User);
    }
}
