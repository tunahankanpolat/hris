package obss.hris.dataAccess;

import obss.hris.model.LdapPeople;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LdapHrRepository extends LdapRepository<LdapPeople> {

    LdapPeople findByUsername(String username);

}