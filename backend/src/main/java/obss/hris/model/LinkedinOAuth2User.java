package obss.hris.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkedinOAuth2User implements OAuth2User {
    private String linkedinId;
    private Map<String, Object> attributes;
    private Collection<SimpleGrantedAuthority> authorities;


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return linkedinId;
    }
}
