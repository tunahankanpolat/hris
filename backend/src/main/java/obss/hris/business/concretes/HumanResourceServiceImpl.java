package obss.hris.business.concretes;

import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.dataAccess.HumanResourceRepository;
import obss.hris.dataAccess.LdapHrRepository;
import obss.hris.model.LdapPeople;
import obss.hris.model.response.GetHumanResourceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HumanResourceServiceImpl implements HumanResourceService {
    private HumanResourceRepository humanResourceRepository;

    private LdapHrRepository ldapHrRepository;


    @Autowired
    public HumanResourceServiceImpl(LdapHrRepository ldapHrRepository) {
        this.ldapHrRepository = ldapHrRepository;
    }

    public GetHumanResourceResponse getByUsername(String username) {
        LdapPeople ldapPeople = ldapHrRepository.findByUsername(username);
        return new GetHumanResourceResponse(ldapPeople.getFirstName(), ldapPeople.getLastName());
    }
}
