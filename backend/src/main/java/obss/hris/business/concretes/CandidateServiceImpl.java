package obss.hris.business.concretes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import obss.hris.business.abstracts.*;
import obss.hris.core.util.jwt.JwtUtils;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.CandidateNotFoundException;
import obss.hris.exception.LinkedinScrapeException;
import obss.hris.model.entity.*;
import obss.hris.model.response.*;
import obss.hris.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private JobApplicationService jobApplicationService;
    private ElasticSearchService elasticSearchService;
    private LinkedinOAuth2UserService linkedinOAuth2UserService;
    @Value("${linkedin.scrape-url}")
    private String linkedinScrapeUrl;


    public CandidateServiceImpl(ModelMapperService modelMapperService, CandidateRepository candidateRepository, JwtUtils jwtUtils, JobApplicationService jobApplicationService, ElasticSearchService elasticSearchService, LinkedinOAuth2UserService linkedinOAuth2UserService) {
        this.modelMapperService = modelMapperService;
        this.candidateRepository = candidateRepository;
        this.jwtUtils = jwtUtils;
        this.jobApplicationService = jobApplicationService;
        this.elasticSearchService = elasticSearchService;
        this.linkedinOAuth2UserService = linkedinOAuth2UserService;
    }

    public void createCandidateIfNotExist(OAuth2User oauth2User){
        Candidate candidate = getCandidateByLinkedinId(oauth2User.getName());
        if(candidate == null){
            Map<String,Object> attributes = oauth2User.getAttributes();
            candidate = this.modelMapperService.forCreate().map(attributes, Candidate.class);
            this.candidateRepository.save(candidate);
            elasticSearchService.saveCandidate(modelMapperService.forCreate().map(candidate, ElkCandidate.class));
        }
    }

    @Override
    public ResponseEntity<String> logout(OAuth2AuthorizedClient authorizedClient) {
        return linkedinOAuth2UserService.revokeUser(authorizedClient).getBody() != null ?
                ResponseEntity.ok("Logout Successful") : ResponseEntity.badRequest().body("Logout Failed");
    }

    @Override
    public Candidate getCandidateByLinkedinId(String linkedinId) {
        return candidateRepository.findByLinkedinId(linkedinId);
    }

    @Override
    public GetCandidateResponse getCandidateByIdForRequest(Long candidateId) {
        Candidate candidate = getCandidateById(candidateId);
        return this.modelMapperService.forResponse().map(candidate, GetCandidateResponse.class);
    }

    @Override
    public Candidate getCandidateById(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException());
        return candidate;
    }

    @Override
    public String scrapeLinkedinProfile(String linkedinUrl, OAuth2User oauth2User) {
        Candidate candidate = getCandidateByLinkedinId(oauth2User.getName());
        if(candidate == null){
            throw new CandidateNotFoundException();
        }
        if(candidate.getAbout() != null || !candidate.getSkills().isEmpty()){
            return "Bilgileriniz zaten linkedin'den çekildi.";
        }
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = linkedinScrapeUrl + "?linkedinUrl=" + linkedinUrl;
        ResponseEntity<CandidateScrapeResponse> response = restTemplate.getForEntity(requestUrl, CandidateScrapeResponse.class);
        if(response.getStatusCode() != ResponseEntity.ok().build().getStatusCode()){
            throw new LinkedinScrapeException(linkedinUrl);
        }
        CandidateScrapeResponse scrapeResponse = response.getBody();
        boolean isCandidateUpdated = false;
        if(scrapeResponse.getAbout() != null){
            candidate.setAbout(scrapeResponse.getAbout());
            isCandidateUpdated = true;
        }
        if(scrapeResponse.getSkills() != null && !scrapeResponse.getSkills().isEmpty()){
            for (String skill : scrapeResponse.getSkills()) {
                candidate.getSkills().add(skill);
            }
            isCandidateUpdated = true;
        }
        if(isCandidateUpdated)
            candidateRepository.save(candidate);
        return "Başarılı bir şekilde bilgileriniz güncellendi.";
    }

    @Override
    public LoginResponse login(OAuth2User oauth2User) {
        return getCandidateLoginResponse();
    }

    private LoginResponse getCandidateLoginResponse() {
        String token = jwtUtils.generateToken(SecurityContextHolder.getContext().getAuthentication());
        LoginResponse loginResponse = new LoginResponse(token);
        return loginResponse;
    }

    @Override
    public void setCandidateAsBanned(Candidate candidate) {
        candidate.setBanned(true);
        candidateRepository.save(candidate);
    }

    @Override
    public List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(Long candidateId, int page, int size) {
        return jobApplicationService.getCandidateJobApplicationsByPage(candidateId, page, size);
    }

//    @Override
//    public JobApplication createJobApplication(CreateJobApplicationRequest jobApplication) {
//        Candidate candidate = candidateService.getCandidateById(jobApplication.getCandidateId());
//        if(candidate.isBanned()){
//            throw new CandidateNotFoundException();
//        }
//        JobPost jobPost = jobPostService.getJobPostById(jobApplication.getJobPostId());
//        if(jobPost == null) {
//            throw new JobPostNotFoundException(jobApplication.getJobPostId());
//        }
//        JobApplication newJobApplication = this.modelMapperService.forCreate().map(jobApplication, JobApplication.class);
//        newJobApplication.setCandidate(candidate);
//        newJobApplication.setJobPost(jobPost);
//        this.jobApplicationRepository.save(newJobApplication);
//        return newJobApplication;
//    }
//    private void banCandidateAndRejectJopApplications(Candidate candidate, String reason) {
//        List<JobApplication> jobApplications = jobApplicationService.getCandidateJobApplications(candidate.getCandidateId());
//        jobApplicationService.batchUpdateStatus(jobApplications, JobApplicationStatus.REJECTED);
//        Blacklist blacklist = new Blacklist();
//        blacklist.setCandidate(candidate);
//        blacklist.setReason(reason);
//        blacklistRepository.save(blacklist);
//    }



//    private CandidateScrapeResponse getUserDataUsingScraping(String linkedinUrl){
//        webDriver.get(linkedinLoginUrl);
//
//        String currentUrl = webDriver.getCurrentUrl();
//        if(currentUrl.contains("/login")){
//            WebElement username = webDriver.findElement(By.id("username"));
//            username.sendKeys(linkedinAccountEmail);
//            WebElement password = webDriver.findElement(By.id("password"));
//            password.sendKeys(linkedinAccountPassword);
//            WebElement form = webDriver.findElement(By.xpath("//form[@class='login__form']"));
//            form.submit();
//        }
//        webDriver.get(linkedinUrl);
//        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
//        try{
//            List<String> skills =  wait.until(webDriver ->
//                    Arrays.stream(
//                            webDriver.findElement(By.cssSelector("#skills+div+div"))
//                                    .getText().split("\n")).distinct().toList());
//            String about =  wait.until(webDriver ->
//                    webDriver.findElement(By.cssSelector("#about+div+div"))
//                            .getText().split("\n"))[0];
//            return new CandidateScrapeResponse(skills,about);
//        }catch (Exception e){
//            return new CandidateScrapeResponse();
//        }
//    }
    @Override
    public OAuth2User loadUserByUsername(String linkedinId) {
        Candidate candidate = getCandidateByLinkedinId(linkedinId);
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
