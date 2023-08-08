package obss.scrape.controller;

import jakarta.servlet.http.HttpServletRequest;
import obss.scrape.dto.CandidateScrapeResponse;
import obss.scrape.service.ScrapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scrape/v1/")
public class ScrapeController {
    private ScrapeService candidateService;

    @Autowired
    public ScrapeController(ScrapeService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("linkedin")
    public ResponseEntity<CandidateScrapeResponse> scrapeSkills(@RequestParam String linkedinUrl) {
        return ResponseEntity.ok(candidateService.scrapeSkills(linkedinUrl));
    }
}
