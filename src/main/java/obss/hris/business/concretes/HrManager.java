package obss.hris.business.concretes;

import obss.hris.business.abstracts.HrService;
import obss.hris.dataAccess.abstracts.HrRepository;
import obss.hris.dataAccess.abstracts.LdapHrRepository;
import obss.hris.entities.concretes.LdapPeople;
import obss.hris.model.request.LoginRequest;
import obss.hris.model.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class HrManager implements HrService {
    private HrRepository hrRepository;

    private LdapHrRepository ldapHrRepository;


    @Autowired
    public HrManager(LdapHrRepository ldapHrRepository) {
        this.ldapHrRepository = ldapHrRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new IllegalArgumentException("Username and password are required.");
        }

        LdapPeople ldapPeople = ldapHrRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());

        if (ldapPeople == null) {
            throw new AuthenticationException(new javax.naming.AuthenticationException("This user does not exist."));
        }
        return new LoginResponse(ldapPeople.toString());
    }
}
