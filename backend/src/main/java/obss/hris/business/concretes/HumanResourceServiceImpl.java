package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.business.abstracts.LdapHumanResourceService;
import obss.hris.core.util.jwt.JwtUtils;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.HumanResourceNotFoundException;
import obss.hris.model.LdapPeople;
import obss.hris.model.entity.HumanResource;
import obss.hris.model.request.HumanResourceLoginRequest;
import obss.hris.model.response.GetHumanResourceResponse;
import obss.hris.model.response.HumanResourceLoginResponse;
import obss.hris.repository.HumanResourceRepository;
import org.modelmapper.TypeMap;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class HumanResourceServiceImpl implements HumanResourceService {
    private HumanResourceRepository humanResourceRepository;
    private LdapHumanResourceService ldapHumanResourceService;
    private ModelMapperService modelMapperService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    @Override
    public HumanResource createHumanResource(HumanResource humanResource) {
        return humanResourceRepository.save(humanResource);
    }
    @Override
    public HumanResource getByUserName(String userName) {
        return humanResourceRepository.findByUserName(userName);
    }

    @Override
    public GetHumanResourceResponse getByLdapUserName(String userName) {
        HumanResource humanResource = humanResourceRepository.findByUserName(userName);
        if(humanResource == null){
            throw new HumanResourceNotFoundException();
        }
        return modelMapperService.forResponse().map(humanResource, GetHumanResourceResponse.class);
    }

    @Override
    public HumanResourceLoginResponse login(HumanResourceLoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        final LdapPeople ldapPeople = ldapHumanResourceService.getByUserName(userName);
        if(ldapPeople == null) {
            throw new HumanResourceNotFoundException();
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        createHumanResourceIfNoExist(ldapPeople);
        HumanResourceLoginResponse loginResponse = modelMapperService.forResponse().map(ldapPeople, HumanResourceLoginResponse.class);
        loginResponse.setToken(jwtUtils.generateToken(ldapPeople));
        return  loginResponse;
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
