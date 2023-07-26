package obss.hris.controller;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.model.response.GetCandidateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/candidate/v1/")
@AllArgsConstructor
public class CandidateController {
    private CandidateService candidateService;
    private JobApplicationService jobApplicationService;

    @GetMapping("me")
    public ResponseEntity<GetCandidateResponse> getCandidateFromSecurityContext() {
        return ResponseEntity.ok(candidateService.getCandidateFromSecurityContext());
    }
    @GetMapping("{candidateId}")
    public ResponseEntity<GetCandidateResponse> getCandidateById(@PathVariable Long candidateId) {
        return ResponseEntity.ok(candidateService.getCandidateByIdForRequest(candidateId));
    }

    @GetMapping("me/jobApplications/{page}/{size}")
    public ResponseEntity<List<GetCandidateJobApplicationResponse>> getCandidateJobApplicationsByPage
            (@PathVariable int page,
             @PathVariable int size) {
        return ResponseEntity.ok(jobApplicationService.getCandidateJobApplicationsByPage(page,size));
    }
}
