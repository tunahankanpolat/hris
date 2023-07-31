package obss.hris.model;

import javax.naming.Name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import java.util.Collection;

@Entry(base = "ou=human_resource", objectClasses = { "top", "person", "organizationalPerson", "inetOrgPerson" })
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LdapPeople implements LdapUserDetails {
    @Id
    private Name dn;

    private @Attribute(name = "uid") String userName;
    private @Attribute(name = "cn") String firstName;
    private @Attribute(name = "sn") String lastName;
    private @Attribute(name = "userPassword") String password;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    private Collection<SimpleGrantedAuthority> authorities;

    public String getDn() {
        return this.dn.toString();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

}