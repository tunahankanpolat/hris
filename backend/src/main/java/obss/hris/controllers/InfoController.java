package obss.hris.controllers;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/info/v1")
public class InfoController {

    @GetMapping("/")
    public Map<String,Object> getInfo(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                        @AuthenticationPrincipal OAuth2User oauth2User) {
        Map<String, Object> map = oauth2User.getAttributes();
        return map;
    }

    @Data
    @Accessors(chain = true)
    private static class Info {
        private String application;
        private Map<String, Object> principal;
    }

}