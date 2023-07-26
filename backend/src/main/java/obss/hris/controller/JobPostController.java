package obss.hris.controller;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.UpdateJobPostRequest;
import obss.hris.model.response.GetJobPostApplicationResponse;
import obss.hris.model.response.GetJobPostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobPosts/v1")
@AllArgsConstructor
public class JobPostController {
    private JobPostService jobPostService;

    @GetMapping("/{page}/{size}")
    public ResponseEntity<List<GetJobPostResponse>> getJobPostsByPage(@PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(jobPostService.jobPostsByPage(page,size));
    }

    @GetMapping("/{jobPostId}")
    public ResponseEntity<GetJobPostResponse> getJob(@PathVariable Long jobPostId) {
        return ResponseEntity.ok(jobPostService.getJobPostByIdForRequest(jobPostId));
    }

    @GetMapping("/jobApplications/{page}/{size}")
    public ResponseEntity<List<GetJobPostApplicationResponse>> getJobPostApplicationsByPage(@RequestParam Long jobPostId, @PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(jobPostService.getJobPostApplicationsByPage(jobPostId,page,size));
    }

    @PostMapping()
    public ResponseEntity<String> createJob(@RequestBody CreateJobPostRequest jobPost) {
        jobPostService.createJobPost(jobPost);
        return ResponseEntity.ok("Job post created successfully");

    }

    @PutMapping()
    public ResponseEntity<String> updateJob( @RequestBody UpdateJobPostRequest jobPost) {
        jobPostService.updateJobPost(jobPost);
        return ResponseEntity.ok("Job post updated successfully");
    }

    @DeleteMapping("/{jobPostId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobPostId) {
        jobPostService.deleteJobPost(jobPostId);
        return ResponseEntity.ok("Job post deleted successfully");
    }

}
