package obss.hris.controller;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.model.entity.JobApplicationStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobApplications/v1")
@AllArgsConstructor
public class JobApplicationController {
    private JobApplicationService jobApplicationService;

    @PutMapping("/{jobApplicationId}/status")
    public ResponseEntity<String> updateJobApplicationStatus(@PathVariable Long jobApplicationId, @RequestParam("jobApplicationStatus")JobApplicationStatus jobApplicationStatus) {
        jobApplicationService.updateStatus(jobApplicationId,jobApplicationStatus);
        return ResponseEntity.ok("Job application status updated successfully");
    }

    @GetMapping("sa")//uygulamada kullanılmıyor, sadece sunum için ekledim
    public ResponseEntity<String> sa() {
        jobApplicationService.sa();
        return ResponseEntity.ok("Job application status updated successfully");
    }
    @DeleteMapping("/{jobApplicationId}")
    public ResponseEntity<String> deleteJobApplication(@PathVariable Long jobApplicationId) {
        jobApplicationService.deleteJobApplication(jobApplicationId);
        return ResponseEntity.ok("Job application deleted successfully");
    }
}
