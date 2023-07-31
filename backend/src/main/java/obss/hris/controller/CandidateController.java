package obss.hris.controller;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.model.response.GetCandidateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate/v1/")
@AllArgsConstructor
public class CandidateController {
    private CandidateService candidateService;
    private JobApplicationService jobApplicationService;

    @GetMapping("me")
    public ResponseEntity<GetCandidateResponse> getCandidate(@AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(candidateService.getCandidateByIdForRequest(oauth2User.getAttribute("candidateId")));
    }

    @GetMapping("{candidateId}")
    public ResponseEntity<GetCandidateResponse> getCandidateById(@PathVariable Long candidateId) {
        return ResponseEntity.ok(candidateService.getCandidateByIdForRequest(candidateId));
    }

    @GetMapping("login")
    public ResponseEntity<String> createCandidateIfNoExistAndScrapeSkills(@RequestParam String linkedinUrl) {
        return ResponseEntity.ok(candidateService.createCandidateIfNoExistAndScrapeSkills(linkedinUrl));
    }

    @GetMapping("token")
    public ResponseEntity<String> getToken(Authentication authentication) {
        return ResponseEntity.ok(candidateService.getToken(authentication));
    }

    @GetMapping("me/jobApplications/{page}/{size}")
    public ResponseEntity<List<GetCandidateJobApplicationResponse>> getCandidateJobApplicationsByPage
            (@PathVariable int page,
             @PathVariable int size,
             @AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(jobApplicationService.getCandidateJobApplicationsByPage
                (oauth2User.getAttribute("candidateId"), page,size));
    }
}
