package obss.hris.business.concretes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.business.abstracts.LdapHumanResourceService;
import obss.hris.core.util.jwt.JwtUtils;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.HumanResourceNotFoundException;
import obss.hris.model.LdapPeople;
import obss.hris.model.entity.HumanResource;
import obss.hris.model.entity.JobApplicationStatus;
import obss.hris.model.request.HumanResourceLoginRequest;
import obss.hris.model.response.GetHumanResourceResponse;
import obss.hris.model.response.GetJobPostApplicationResponse;
import obss.hris.model.response.LoginResponse;
import obss.hris.repository.HumanResourceRepository;
import org.modelmapper.TypeMap;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

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
        return new LoginResponse(token);
    }
    @Override
    public List<GetJobPostApplicationResponse> getJobPostApplicationsByPage(Long jobPostId, int page, int size, JobApplicationStatus jobApplicationStatus, String searchKeyword) {
        if(jobApplicationStatus != null && searchKeyword != null) {
            return jobApplicationService.getJobPostApplicationsByPageByStatusBySearchKeyword(jobPostId, page, size, jobApplicationStatus, searchKeyword);
        } else if (jobApplicationStatus != null){
            return jobApplicationService.getJobPostApplicationsByPageByStatus(jobPostId, page, size, jobApplicationStatus);
        } else if (searchKeyword != null){
            return jobApplicationService.getJobPostApplicationsByPageBySearchKeyword(jobPostId, page, size, searchKeyword);
        }else {
            return jobApplicationService.getJobPostApplicationsByPage(jobPostId, page, size);
        }
    }

    private void createHumanResourceIfNoExist(LdapPeople ldapPeople) {
        HumanResource humanResource = humanResourceRepository.findByUserName(ldapPeople.getUsername());
        if (humanResource == null) {
            TypeMap<LdapPeople, HumanResource> typeMap = this.modelMapperService.forCreate().getTypeMap(LdapPeople.class, HumanResource.class);
            if(typeMap == null){
                typeMap = this.modelMapperService.forCreate().createTypeMap(LdapPeople.class, HumanResource.class);

                typeMap.addMappings(
                        mapper -> mapper.map(LdapPeople::getUsername, HumanResource::setUserName)
                );
            }
            humanResource = modelMapperService.forCreate().map(ldapPeople, HumanResource.class);
            createHumanResource(humanResource);
        }
    }
}
