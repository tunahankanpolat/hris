package obss.hris.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.model.response.GetCandidateResponse;
import obss.hris.model.response.LoginResponse;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal OAuth2User oauth2User, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        return candidateService.logout(authorizedClient);
    }

    @Transactional
    @GetMapping("scrape/linkedin")
    public ResponseEntity<String> scrapeLinkedinProfile(
            @RequestParam String linkedinUrl, @AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(candidateService.scrapeLinkedinProfile(linkedinUrl, oauth2User));
    }

    @GetMapping("login")
    public ResponseEntity<LoginResponse> login(@AuthenticationPrincipal OAuth2User oauth2User) {
        return ResponseEntity.ok(candidateService.login(oauth2User));
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
