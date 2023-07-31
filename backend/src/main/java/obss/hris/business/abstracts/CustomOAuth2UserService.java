package obss.hris.business.abstracts;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CustomOAuth2UserService {
    OAuth2User loadUserByUsername(String linkedinId);
}
