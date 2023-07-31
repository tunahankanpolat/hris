package obss.hris.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

@Configuration
@RequiredArgsConstructor
public class LdapConfig {
    @Value("${ldap.user.dn.pattern}")
    private String userDnPatterns;

    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${ldap.partitionSuffix}")
    private String ldapPartitionSuffix;

    @Value("${ldap.principal}")
    private String ldapPrincipal;

    @Value("${ldap.password}")
    private String ldapPassword;

    @Bean
    LdapAuthoritiesPopulator authorities(BaseLdapPathContextSource contextSource) {
        String groupSearchBase = "ou=groups";
        DefaultLdapAuthoritiesPopulator authorities =
                new DefaultLdapAuthoritiesPopulator(contextSource, groupSearchBase);
        authorities.setGroupSearchFilter("member={0}");
        return authorities;
    }

    @Bean
    AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource, LdapAuthoritiesPopulator authorities) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
        factory.setUserDnPatterns(userDnPatterns);
        factory.setLdapAuthoritiesPopulator(authorities);
        return factory.createAuthenticationManager();
    }
    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setBase(ldapPartitionSuffix);
        contextSource.setUserDn(ldapPrincipal);
        contextSource.setPassword(ldapPassword);
        return contextSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
