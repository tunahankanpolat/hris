package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.LdapHumanResourceService;
import obss.hris.model.LdapPeople;
import obss.hris.repository.LdapHumanResourceRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LdapHumanResourceServiceImp implements LdapHumanResourceService {

    private LdapHumanResourceRepository ldapHumanResourceRepository;

    @Override
    public LdapPeople getByUserName(String userName) {
        return  ldapHumanResourceRepository.findByUserName(userName);
    }
}
