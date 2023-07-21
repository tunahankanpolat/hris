package obss.hris.entities.concretes;

import javax.naming.Name;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry(base = "ou=people", objectClasses = { "top", "person", "organizationalPerson", "inetOrgPerson" })
@AllArgsConstructor
@NoArgsConstructor
public class LdapPeople {
    @Id
    private Name id;

    private @Attribute(name = "uid") String username;
    private @Attribute(name = "userPassword") String password;


    @Override
    public String toString() {
        return username;
    }

}