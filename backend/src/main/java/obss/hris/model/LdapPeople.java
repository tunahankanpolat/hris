package obss.hris.model;

import javax.naming.Name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry(base = "ou=people", objectClasses = { "top", "person", "organizationalPerson", "inetOrgPerson" })
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LdapPeople {
    @Id
    private Name id;

    private @Attribute(name = "uid") String username;
    private @Attribute(name = "cn") String firstName;
    private @Attribute(name = "sn") String lastName;


    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}