package obss.hris.controller;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.model.request.HumanResourceLoginRequest;
import obss.hris.model.response.GetHumanResourceResponse;
import obss.hris.model.response.GetJobPostResponse;
import obss.hris.model.response.HumanResourceLoginResponse;
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
        return ResponseEntity.ok(humanResourceService.getByLdapUserName(principal.getName()));
    }

    @GetMapping("me/jobPosts/{page}/{size}")
    public ResponseEntity<List<GetJobPostResponse>> getHumanResourceJobPostsByPage(@PathVariable int page, @PathVariable int size) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(jobPostService.getJobPostsByCreatorByPage(userName,page,size));
    }

    @PostMapping("login")
    public ResponseEntity<HumanResourceLoginResponse> login(@RequestBody HumanResourceLoginRequest humanResourceLoginRequest){
        return ResponseEntity.ok(humanResourceService.login(humanResourceLoginRequest));
    }

}
