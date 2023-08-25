package obss.hris.config;

import lombok.AllArgsConstructor;
import obss.hris.business.concretes.HttpCookieOAuth2AuthorizationRequestRepository;
import obss.hris.business.concretes.LinkedinOAuth2UserService;
import obss.hris.core.util.handler.OAuth2AuthenticationFailureHandler;
import obss.hris.core.util.handler.OAuth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private CorsConfigurationSource corsConfigurationSource;
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private static final String HR_ROLE = "HR_USER";
    private static final String CANDIDATE_ROLE = "OAUTH2_USER";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(configurer -> configurer.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/error", "/api/hr/v1/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/jobPosts/v1/{page}/{size}").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/candidate/v1/{candidateId}/**").hasAnyAuthority(HR_ROLE, CANDIDATE_ROLE)
                        .requestMatchers(HttpMethod.GET,"/api/candidate/v1/**").hasAnyAuthority(CANDIDATE_ROLE)
                        .requestMatchers(HttpMethod.DELETE,"/api/jobApplications/v1").hasAuthority(CANDIDATE_ROLE)
                        .requestMatchers(HttpMethod.GET,"/api/jobPosts/v1/{jobPostId}/apply").hasAuthority(CANDIDATE_ROLE)

                        .requestMatchers("/api/hr/v1/**").hasAuthority(HR_ROLE)
                        .requestMatchers(HttpMethod.POST,"/api/blacklist/v1/banCandidate").hasAnyAuthority(HR_ROLE)
                        .requestMatchers(HttpMethod.PUT,"/api/jobApplications/v1/{jobApplicationId}/status").hasAuthority(HR_ROLE)
                        .requestMatchers("/api/jobPosts/v1/**").hasAuthority(HR_ROLE)
                        .requestMatchers(HttpMethod.GET,"/api/search/v1/**").hasAuthority(HR_ROLE)

                        .anyRequest().authenticated()
                )
                .sessionManagement(config ->
                    config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login(oauth2 -> oauth2
                        .tokenEndpoint(token
                                -> token.accessTokenResponseClient(linkedinTokenResponseClient()))
                        .authorizationEndpoint(authorization -> authorization
                                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                        )
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(this.linkedinOAuth2UserService))
                ).formLogin(withDefaults())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"" + authException.getMessage() + "\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"" + accessDeniedException.getMessage() + "\"}");
                        }))
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