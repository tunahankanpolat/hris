package obss.hris.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.model.response.GetCandidateResponse;
import obss.hris.model.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate/v1/")
@AllArgsConstructor
public class CandidateController {
    private CandidateService candidateService;

    @GetMapping("me")
    public ResponseEntity<GetCandidateResponse> getCandidate(@AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(candidateService.getCandidateByIdForRequest(oauth2User.getAttribute("candidateId")));
    }

    @GetMapping("{candidateId}")
    public ResponseEntity<GetCandidateResponse> getCandidateById(@PathVariable Long candidateId) {
        return ResponseEntity.ok(candidateService.getCandidateByIdForRequest(candidateId));
    }

    @GetMapping("scrape-skills")
    public ResponseEntity<LoginResponse> scrapeSkillsAndCreateCandidateIfNotExist(@RequestParam String linkedinUrl, HttpServletRequest request) {
        return ResponseEntity.ok(candidateService.scrapeSkillsAndCreateCandidateIfNotExist(linkedinUrl, request));
    }

    @GetMapping("login")
    public ResponseEntity<LoginResponse> login(HttpServletRequest request) {
        return ResponseEntity.ok(candidateService.login(request));
    }

    @GetMapping("me/jobApplications/{page}/{size}")
    public ResponseEntity<List<GetCandidateJobApplicationResponse>> getCandidateJobApplicationsByPage
            (@PathVariable int page,
             @PathVariable int size,
             @AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(candidateService.getCandidateJobApplicationsByPage(oauth2User.getAttribute("candidateId"),page, size));
    }

    @GetMapping("{candidateId}/jobApplications/{page}/{size}")
    public ResponseEntity<List<GetCandidateJobApplicationResponse>> getCandidateJobApplicationsByPageForHR
            (@PathVariable int page,
             @PathVariable int size,
             @PathVariable Long candidateId) {
        return ResponseEntity.ok(candidateService.getCandidateJobApplicationsByPage(candidateId,page, size));
    }
}
