package obss.hris.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.UpdateJobPostRequest;
import obss.hris.model.response.GetJobPostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<GetJobPostResponse> getJobPost(@PathVariable Long jobPostId) {
        return ResponseEntity.ok(jobPostService.getJobPostByIdForRequest(jobPostId));
    }

    @GetMapping("/{jobPostId}/apply")
    public ResponseEntity<String> applyToJobPost(@PathVariable Long jobPostId, @AuthenticationPrincipal OAuth2User oauth2User) {
        jobPostService.applyToJobPost(jobPostId, oauth2User.getAttribute("candidateId"));
        return ResponseEntity.ok("İş başvurunuz oluşturuldu");
    }

    @PostMapping()
    public ResponseEntity<String> createJobPost(@Valid @RequestBody CreateJobPostRequest jobPost, Principal principal) {
        jobPostService.createJobPost(principal.getName(), jobPost);
        return ResponseEntity.ok("İş ilanı oluşturuldu.");
    }

    @PutMapping()
    public ResponseEntity<String> updateJobPost(@Valid @RequestBody UpdateJobPostRequest jobPost) {
        jobPostService.updateJobPost(jobPost);
        return ResponseEntity.ok("İş ilanı güncellendi.");
    }

    @DeleteMapping("/{jobPostId}")
    public ResponseEntity<String> deleteJobPost(@PathVariable Long jobPostId) {
        jobPostService.deleteJobPost(jobPostId);
        return ResponseEntity.ok("İş ilanı silindi.");
    }

}
