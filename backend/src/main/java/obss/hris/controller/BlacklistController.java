package obss.hris.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.BlacklistService;
import obss.hris.model.request.BanRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blacklist/v1/")
@AllArgsConstructor
public class BlacklistController {
    private BlacklistService blacklistService;

    @PostMapping("banCandidate")
    @Transactional
    public ResponseEntity<String> banCandidate(@Valid @RequestBody BanRequest banRequest) {
        blacklistService.banCandidate(banRequest);
        return ResponseEntity.ok("Candidate banned successfully");
    }
}
