package obss.hris.core.util.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CustomOAuth2UserService;
import obss.hris.business.abstracts.LdapHumanResourceService;
import obss.hris.model.LdapPeople;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;
    private LdapHumanResourceService ldapHumanResourceService;
    private CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        String userName = null;
        String jwtToken = null;
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authorizationHeader.substring(7);
        userName = jwtUtils.extractUsername(jwtToken);
        String authority = jwtUtils.extractAuthority(jwtToken);
        if (authority == null && userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            LdapPeople ldapPeople = ldapHumanResourceService.getByUserName(userName);
            ldapPeople.getAuthorities().add(new SimpleGrantedAuthority("HR_USER"));
            if (jwtUtils.isTokenValid(jwtToken, ldapPeople)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(ldapPeople, null, ldapPeople.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        if (authority != null &&authority.equals("OAUTH2_USER") && userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            OAuth2User oAuth2User = customOAuth2UserService.loadUserByUsername(userName);

            if (jwtUtils.isTokenValid(jwtToken, oAuth2User)) {
                OAuth2AuthenticationToken authToken =
                        new OAuth2AuthenticationToken(oAuth2User, oAuth2User.getAuthorities(), "linkedin");
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
