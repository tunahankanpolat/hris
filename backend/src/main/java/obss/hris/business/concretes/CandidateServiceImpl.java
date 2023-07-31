package obss.hris.business.concretes;

import obss.hris.business.abstracts.CandidateService;
import obss.hris.business.abstracts.CustomOAuth2UserService;
import obss.hris.core.util.jwt.JwtUtils;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.CandidateNotFoundException;
import obss.hris.model.entity.Candidate;
import obss.hris.model.response.CandidateScrapeResponse;
import obss.hris.model.response.GetCandidateResponse;
import obss.hris.repository.CandidateRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CandidateServiceImpl implements CandidateService, CustomOAuth2UserService {
    private ModelMapperService modelMapperService;
    private CandidateRepository candidateRepository;
    private JwtUtils jwtUtils;
    private WebDriver webDriver;
    @Value("${linkedin.login-url}")
    private String linkedinLoginUrl;

    @Value("${linkedin.account-email}")
    private String linkedinAccountEmail;

    @Value("${linkedin.account-password}")
    private String linkedinAccountPassword;

    public CandidateServiceImpl(ModelMapperService modelMapperService, CandidateRepository candidateRepository, JwtUtils jwtUtils, WebDriver webDriver) {
        this.modelMapperService = modelMapperService;
        this.candidateRepository = candidateRepository;
        this.jwtUtils = jwtUtils;
        this.webDriver = webDriver;
    }

    @Override
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate getCandidateByLinkedinId(String linkedinId) {
        return candidateRepository.findByLinkedinId(linkedinId);
    }

    @Override
    public GetCandidateResponse getCandidateByIdForRequest(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        if (candidate == null) {
            throw new CandidateNotFoundException();
        }
        return this.modelMapperService.forResponse().map(candidate, GetCandidateResponse.class);
    }

    @Override
    public Candidate getCandidateById(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
        if (candidate == null) {
            throw new CandidateNotFoundException();
        }
        return candidate;
    }

    @Override
    public String createCandidateIfNoExistAndScrapeSkills(String linkedinUrl) {
        OAuth2User oauth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> attributes = oauth2User.getAttributes();
        Candidate candidate = this.candidateRepository.findByLinkedinId(attributes.get("linkedinId").toString());
        if(candidate == null){
            candidate = this.modelMapperService.forCreate().map(attributes, Candidate.class);
            CandidateScrapeResponse scrapeResponse = getUserDataUsingScraping(linkedinUrl);
            candidate.setSkills(scrapeResponse.getSkills());
            candidate.setAbout(scrapeResponse.getAbout());
            candidateRepository.save(candidate);
        }
        return jwtUtils.generateToken(SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    public String getToken(Authentication authentication) {
        return jwtUtils.generateToken(authentication);
    }

    private CandidateScrapeResponse getUserDataUsingScraping(String linkedinUrl){
        webDriver.get(linkedinLoginUrl);

        String currentUrl = webDriver.getCurrentUrl();
        if(currentUrl.contains("/login")){
            WebElement username = webDriver.findElement(By.id("username"));
            username.sendKeys(linkedinAccountEmail);
            WebElement password = webDriver.findElement(By.id("password"));
            password.sendKeys(linkedinAccountPassword);
            WebElement form = webDriver.findElement(By.xpath("//form[@class='login__form']"));
            form.submit();
        }
        webDriver.get(linkedinUrl);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        try{
            List<String> skills =  wait.until(webDriver ->
                    Arrays.stream(
                            webDriver.findElement(By.cssSelector("#skills+div+div"))
                                    .getText().split("\n")).distinct().toList());
            String about =  wait.until(webDriver ->
                    webDriver.findElement(By.cssSelector("#about+div+div"))
                            .getText().split("\n"))[0];
            return new CandidateScrapeResponse(skills,about);
        }catch (Exception e){
            return new CandidateScrapeResponse();
        }
    }
    @Override
    public OAuth2User loadUserByUsername(String linkedinId) {
        Candidate candidate = candidateRepository.findByLinkedinId(linkedinId);
        if (candidate != null) {
            return new DefaultOAuth2User(
                    Arrays.asList(new SimpleGrantedAuthority("OAUTH2_USER")),
                    getAttributes(candidate),
                    "linkedinId");
        }else{
            throw new CandidateNotFoundException();
        }
    }

    private Map<String, Object> getAttributes(Candidate candidate){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("firstName", candidate.getFirstName());
        attributes.put("lastName", candidate.getLastName());
        attributes.put("profilePicture", candidate.getProfilePicture());
        attributes.put("email", candidate.getEmail());
        attributes.put("linkedinId", candidate.getLinkedinId());
        attributes.put("candidateId", candidate.getCandidateId());
        return attributes;
    }
}
