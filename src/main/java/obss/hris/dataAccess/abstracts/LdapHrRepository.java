package obss.hris.dataAccess.abstracts;

import obss.hris.entities.concretes.LdapPeople;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LdapHrRepository extends LdapRepository<LdapPeople> {

    LdapPeople findByUsernameAndPassword(String username, String password);

}