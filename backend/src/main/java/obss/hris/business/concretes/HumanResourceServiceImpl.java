package obss.hris.business.concretes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.business.abstracts.LdapHumanResourceService;
import obss.hris.core.util.jwt.JwtUtils;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.HumanResourceNotFoundException;
import obss.hris.model.LdapPeople;
import obss.hris.model.entity.HumanResource;
import obss.hris.model.entity.JobApplicationStatus;
import obss.hris.model.entity.JobPost;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.HumanResourceLoginRequest;
import obss.hris.model.response.*;
import obss.hris.repository.HumanResourceRepository;
import org.modelmapper.TypeMap;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class HumanResourceServiceImpl implements HumanResourceService {
    private HumanResourceRepository humanResourceRepository;
    private LdapHumanResourceService ldapHumanResourceService;
    private ModelMapperService modelMapperService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private JobApplicationService jobApplicationService;

    @Override
    public HumanResource createHumanResource(HumanResource humanResource) {
        return humanResourceRepository.save(humanResource);
    }
    @Override
    public HumanResource getByUserName(String userName) {
        HumanResource humanResource = humanResourceRepository.findByUserName(userName);
        if (humanResource == null) {
            throw new HumanResourceNotFoundException();
        }
        return humanResource;
    }

    @Override
    public GetHumanResourceResponse getByUserNameForRequest(String userName) {
        HumanResource humanResource = humanResourceRepository.findByUserName(userName);
        if(humanResource == null){
            throw new HumanResourceNotFoundException();
        }
        return modelMapperService.forResponse().map(humanResource, GetHumanResourceResponse.class);
    }

    @Override
    public LoginResponse login(HumanResourceLoginRequest loginRequest, HttpServletRequest request) {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        final LdapPeople ldapPeople = ldapHumanResourceService.getByUserName(userName);
        if(ldapPeople == null) {
            throw new HumanResourceNotFoundException();
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        createHumanResourceIfNoExist(ldapPeople);
        String token = jwtUtils.generateToken(ldapPeople);
        LoginResponse loginResponse = new LoginResponse(token);
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate(); // Session'ı sonlandır
        }
        return loginResponse;
    }
    @Override
    public List<GetJobPostApplicationResponse> getJobPostApplicationsByPage(Long jobPostId, int page, int size, JobApplicationStatus jobApplicationStatus) {
        return jobApplicationService.getJobPostApplicationsByPage(jobPostId, page, size, jobApplicationStatus);
    }

    @Override
    public List<GetJobPostResponse> getJobPostsByPage(String userName, int page, int size) {
        HumanResource humanResource = getByUserName(userName);
        List<JobPost> jobPosts = humanResource.getJobPosts();
        int totalApplications = jobPosts.size();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalApplications);
        List<JobPost> pagedApplications = jobPosts.subList(startIndex, endIndex);
        return pagedApplications.stream().map(jobPost ->
                modelMapperService.forResponse().map(jobPost, GetJobPostResponse.class)).toList();
    }

    private void createHumanResourceIfNoExist(LdapPeople ldapPeople) {
        HumanResource humanResource = humanResourceRepository.findByUserName(ldapPeople.getUsername());
        if (humanResource == null) {
            TypeMap<LdapPeople, HumanResource> typeMap = this.modelMapperService.forCreate().getTypeMap(LdapPeople.class, HumanResource.class);
            if(typeMap == null){
                typeMap = this.modelMapperService.forCreate().createTypeMap(LdapPeople.class, HumanResource.class);

                typeMap.addMappings(
                        mapper -> mapper.map(src -> src.getUsername(), HumanResource::setUserName)
                );
            }
            humanResource = modelMapperService.forCreate().map(ldapPeople, HumanResource.class);
            createHumanResource(humanResource);
        }
    }
}
