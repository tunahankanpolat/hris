package obss.hris.controller;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.model.request.CreateJobApplicationRequest;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobApplication/v1")
@AllArgsConstructor
public class JobApplicationController {
    private JobApplicationService jobApplicationService;

    @PostMapping()
    public ResponseEntity<String> createJobApplication(@RequestBody CreateJobApplicationRequest jobApplication) {
        jobApplicationService.createJobApplication(jobApplication);
        return ResponseEntity.ok("Job application created successfully");
    }

    @GetMapping({"/{page}/{size}"})
    public ResponseEntity<List<GetCandidateJobApplicationResponse>> getCandidateJobApplicationsByPage(@RequestParam Long candidateId, @PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok( jobApplicationService.getCandidateJobApplicationsByPage(candidateId,page,size));
    }


}
