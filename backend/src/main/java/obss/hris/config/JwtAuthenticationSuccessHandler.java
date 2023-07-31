package obss.hris.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.model.entity.Candidate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private CandidateService candidateService;
    @Value("${react.base-url}")
    private String reactBaseUrl;

    @Value("${react.scrape-path}")
    private String reactScrapePath;

    @Value("${react.login-success-path}")
    private String reactLoginSuccessPath;

    public JwtAuthenticationSuccessHandler(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Candidate candidate = candidateService.getCandidateByLinkedinId(authentication.getName());
        if(candidate == null){
            response.sendRedirect(reactBaseUrl + reactScrapePath);
        }else {
            response.sendRedirect(reactBaseUrl + reactLoginSuccessPath);
        }
    }

}
