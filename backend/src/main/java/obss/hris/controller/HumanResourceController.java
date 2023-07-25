package obss.hris.controller;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.business.abstracts.LdapHumanResourceService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.model.LdapPeople;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.UpdateJobPostRequest;
import obss.hris.model.response.GetHumanResourceResponse;
import obss.hris.model.response.GetJobPostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.LdapTemplate;
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
    private LdapHumanResourceService ldapHumanResourceService;

    private ModelMapperService modelMapperService;
    private LdapTemplate ldapTemplate;
    @GetMapping("me")
    public ResponseEntity<GetHumanResourceResponse> getHumanResource(Principal principal) {
        LdapPeople ldapPeople = ldapHumanResourceService.getByUserName(principal.getName());
        GetHumanResourceResponse humanResource = modelMapperService.forResponse().map(ldapPeople, GetHumanResourceResponse.class);
        return ResponseEntity.ok(humanResource);
    }

    @GetMapping("me/jobPosts")
    public ResponseEntity<List<GetJobPostResponse>> getMyJobPosts() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(jobPostService.getJobPostsByCreator(userName));
    }

    @GetMapping("jobPosts")
    public ResponseEntity<List<GetJobPostResponse>> getJobPosts() {
        return ResponseEntity.ok(jobPostService.getJobPosts());
    }

    @GetMapping("me/jobPostsByPage/{page}/{size}")
    public ResponseEntity<List<GetJobPostResponse>> getMyJobPostsByPage(@PathVariable int page, @PathVariable int size) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(jobPostService.getJobPostsByCreatorByPage(userName,page,size));
    }


    @GetMapping("jobPostsByPage/{page}/{size}")
    public ResponseEntity<List<GetJobPostResponse>> getJobPostsByPage(@PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(jobPostService.jobPostsByPage(page,size));
    }

    @GetMapping("jobPosts/{jobPostId}")
    public ResponseEntity<GetJobPostResponse> getJob(@PathVariable Long jobPostId) {
        return ResponseEntity.ok(jobPostService.getJobPost(jobPostId));
    }

    @PostMapping("jobPosts")
    public ResponseEntity<String> createJob(@RequestBody CreateJobPostRequest jobPost) {
        jobPostService.createJobPost(jobPost);
        return ResponseEntity.ok("Job post created successfully");

    }

    @PutMapping("jobPosts")
    public ResponseEntity<String> updateJob( @RequestBody UpdateJobPostRequest jobPost) {
        jobPostService.updateJobPost(jobPost);
        return ResponseEntity.ok("Job post updated successfully");
    }

    @DeleteMapping("jobPosts/{jobPostId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobPostId) {
        jobPostService.deleteJobPost(jobPostId);
        return ResponseEntity.ok("Job post deleted successfully");
    }
}
