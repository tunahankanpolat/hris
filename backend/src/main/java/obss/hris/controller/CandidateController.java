package obss.hris.controller;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.model.response.GetCandidateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/candidate/v1/")
@AllArgsConstructor
public class CandidateController {
    private CandidateService candidateService;

    @GetMapping("me")
    public ResponseEntity<GetCandidateResponse> getCandidate(@AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(candidateService.getCandidate(oauth2User));
    }
}
