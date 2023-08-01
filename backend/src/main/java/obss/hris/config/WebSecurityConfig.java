package obss.hris.config;

import lombok.AllArgsConstructor;
import obss.hris.business.concretes.LinkedinOAuth2UserService;
import obss.hris.core.util.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.DefaultMapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    private LinkedinOAuth2UserService linkedinOAuth2UserService;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private CorsConfigurationSource corsConfigurationSource;

    private final String HR_ROLE = "HR_USER";
    private final String CANDIDATE_ROLE = "OAUTH2_USER";
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource).and()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET,"/login/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/hr/v1/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/candidate/v1/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/candidate/v1/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/candidate/v1/me").hasAuthority(CANDIDATE_ROLE)
                        .requestMatchers(HttpMethod.GET,"/api/hr/v1/me").hasAuthority(HR_ROLE)
                        .requestMatchers(HttpMethod.GET,"/api/jobPosts/v1/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/jobPosts/v1/jobApplications/**").hasAuthority(HR_ROLE)
                        .requestMatchers(HttpMethod.POST,"/api/jobPosts/v1").hasAuthority(HR_ROLE)
                        .requestMatchers(HttpMethod.PUT,"/api/jobPosts/v1").hasAuthority(HR_ROLE)
                        .requestMatchers(HttpMethod.DELETE,"/api/jobPosts/v1").hasAuthority(HR_ROLE)
                        .requestMatchers(HttpMethod.GET,"/api/jobApplications/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/jobApplications/v1").hasAuthority(CANDIDATE_ROLE)
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .tokenEndpoint(token
                                -> token.accessTokenResponseClient(linkedinTokenResponseClient()))
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(this.linkedinOAuth2UserService))
                                .successHandler(jwtAuthenticationSuccessHandler)
                ).formLogin(withDefaults())
                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    private static DefaultAuthorizationCodeTokenResponseClient linkedinTokenResponseClient() {
        var defaultMapConverter = new DefaultMapOAuth2AccessTokenResponseConverter();
        Converter<Map<String, Object>, OAuth2AccessTokenResponse> linkedinMapConverter = tokenResponse -> {
            var withTokenType = new HashMap<>(tokenResponse);
            withTokenType.put(OAuth2ParameterNames.TOKEN_TYPE, OAuth2AccessToken.TokenType.BEARER.getValue());
            return defaultMapConverter.convert(withTokenType);
        };

        var httpConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        httpConverter.setAccessTokenResponseConverter(linkedinMapConverter);

        var restOperations = new RestTemplate(List.of(new FormHttpMessageConverter(), httpConverter));
        restOperations.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        var client = new DefaultAuthorizationCodeTokenResponseClient();
        client.setRestOperations(restOperations);
        return client;
    }




}