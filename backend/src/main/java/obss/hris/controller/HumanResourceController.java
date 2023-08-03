package obss.hris.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.model.entity.JobApplicationStatus;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.HumanResourceLoginRequest;
import obss.hris.model.request.UpdateJobPostRequest;
import obss.hris.model.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/hr/v1/")
@AllArgsConstructor
public class HumanResourceController {
    private HumanResourceService humanResourceService;
    private JobPostService jobPostService;

    @GetMapping("me")
    public ResponseEntity<GetHumanResourceResponse> getHumanResource(Principal principal) {
        return ResponseEntity.ok(humanResourceService.getByUserNameForRequest(principal.getName()));
    }

    @GetMapping("me/jobPosts/{page}/{size}")
    public ResponseEntity<List<GetJobPostResponse>> getHumanResourceJobPostsByPage(@PathVariable int page, @PathVariable int size, Principal principal) {
        return ResponseEntity.ok(jobPostService.getJobPostsByCreatorByPage(principal.getName(),page,size));
    }

    @GetMapping("me/jobPosts/{jobPostId}/jobApplications/{page}/{size}")
    public ResponseEntity<List<GetJobPostApplicationResponse>> getJobPostApplicationsByPage(
            @PathVariable Long jobPostId,
            @PathVariable int page, @PathVariable int size,
            @RequestParam(value = "jobApplicationStatus", required = false) JobApplicationStatus jobApplicationStatus) {
        return ResponseEntity.ok(humanResourceService.getJobPostApplicationsByPage(jobPostId,page,size,jobApplicationStatus));
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody HumanResourceLoginRequest humanResourceLoginRequest, HttpServletRequest request){
        return ResponseEntity.ok(humanResourceService.login(humanResourceLoginRequest, request));
    }
}
