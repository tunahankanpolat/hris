//package obss.hris.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.*;
//import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
//import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.web.bind.annotation.*;
//
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@RestController
//@RequestMapping()
//public class CandidateController {
//
//
//    @GetMapping("/linkedin")
//    public String linkedin(@AuthenticationPrincipal OAuth2User principal) {
//        // LinkedIn'den alınan kimlik doğrulama bilgilerini işleyin
//        System.out.println(principal.toString());
//
//        // Bu bilgileri kullanarak gerekli işlemleri yapın veya kullanıcıyı oturum açtırın
//        // ...
//
//        return principal.toString();
//    }
//}
