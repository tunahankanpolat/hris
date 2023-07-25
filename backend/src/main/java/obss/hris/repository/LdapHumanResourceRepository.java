package obss.hris.repository;

import obss.hris.model.LdapPeople;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LdapHumanResourceRepository extends LdapRepository<LdapPeople> {

    LdapPeople findByUserName(String userName);

}