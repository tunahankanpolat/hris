package obss.hris.business.concretes;

import obss.hris.business.abstracts.CandidateService;
import obss.hris.business.abstracts.CustomOAuth2UserService;
import obss.hris.business.abstracts.ElasticSearchService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.core.util.jwt.JwtUtils;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.CandidateNotFoundException;
import obss.hris.exception.LinkedinScrapeException;
import obss.hris.model.entity.Candidate;
import obss.hris.model.entity.ElkCandidate;
import obss.hris.model.response.CandidateScrapeResponse;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.model.response.GetCandidateResponse;
import obss.hris.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CandidateServiceImpl implements CandidateService, CustomOAuth2UserService {
    private ModelMapperService modelMapperService;
    private CandidateRepository candidateRepository;
    private JobApplicationService jobApplicationService;
    private ElasticSearchService elasticSearchService;
    private LinkedinOAuth2UserService linkedinOAuth2UserService;
    @Value("${linkedin.scrape-url}")
    private String linkedinScrapeUrl;

    @Autowired
    public CandidateServiceImpl(ModelMapperService modelMapperService, CandidateRepository candidateRepository, JobApplicationService jobApplicationService, ElasticSearchService elasticSearchService, LinkedinOAuth2UserService linkedinOAuth2UserService) {
        this.modelMapperService = modelMapperService;
        this.candidateRepository = candidateRepository;
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
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException());
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
        if(scrapeResponse != null && scrapeResponse.getAbout() != null){
            candidate.setAbout(scrapeResponse.getAbout());
            isCandidateUpdated = true;
        }
        if(scrapeResponse != null &&  scrapeResponse.getSkills() != null && !scrapeResponse.getSkills().isEmpty()){
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
    public void setCandidateAsBanned(Candidate candidate) {
        candidate.setBanned(true);
        candidateRepository.save(candidate);
    }

    @Override
    public List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(Long candidateId, int page, int size) {
        return jobApplicationService.getCandidateJobApplicationsByPage(candidateId, page, size);
    }

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
