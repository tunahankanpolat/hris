package obss.hris.controller;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.business.abstracts.LdapHumanResourceService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.model.LdapPeople;
import obss.hris.model.response.GetHumanResourceResponse;
import obss.hris.model.response.GetJobPostResponse;
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
    private LdapHumanResourceService ldapHumanResourceService;

    private ModelMapperService modelMapperService;
    @GetMapping("me")
    public ResponseEntity<GetHumanResourceResponse> getHumanResource(Principal principal) {
        LdapPeople ldapPeople = ldapHumanResourceService.getByUserName(principal.getName());
        GetHumanResourceResponse humanResource = modelMapperService.forResponse().map(ldapPeople, GetHumanResourceResponse.class);
        return ResponseEntity.ok(humanResource);
    }

    @GetMapping("me/jobPosts/{page}/{size}")
    public ResponseEntity<List<GetJobPostResponse>> getHumanResourceJobPostsByPage(@PathVariable int page, @PathVariable int size) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(jobPostService.getJobPostsByCreatorByPage(userName,page,size));
    }

}
