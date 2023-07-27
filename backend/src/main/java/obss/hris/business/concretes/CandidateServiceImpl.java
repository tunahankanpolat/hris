package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.CandidateNotFoundException;
import obss.hris.model.entity.Candidate;
import obss.hris.model.response.GetCandidateResponse;
import obss.hris.repository.CandidateRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private ModelMapperService modelMapperService;
    private CandidateRepository candidateRepository;

    private WebDriver webDriver;

    @Override
    public GetCandidateResponse getCandidateFromSecurityContext() {
        OAuth2User oauth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = oauth2User.getAttributes();
        return this.modelMapperService.forResponse().map(map, GetCandidateResponse.class);
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
    public List<String> scrapeSkillsFromLinkedin(String linkedinUrl) {
        List<String> skills = getUserDataUsingScraping(linkedinUrl);
        OAuth2User oauth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Candidate candidate = getCandidateById(Long.valueOf(oauth2User.getName()));
        candidate.getSkills().addAll(skills);
        candidateRepository.save(candidate);
        return skills;
    }

    public List<String> getUserDataUsingScraping(String linkedinUrl){
        webDriver.get("https://www.linkedin.com/login");

        //bulunduğu urli çeksin
        String currentUrl = webDriver.getCurrentUrl();
        if(currentUrl.contains("/login")){
            WebElement username = webDriver.findElement(By.id("username"));
            username.sendKeys("hris.appx@gmail.com");
            WebElement password = webDriver.findElement(By.id("password"));
            password.sendKeys("#hrisappx27");
            WebElement form = webDriver.findElement(By.xpath("//form[@class='login__form']"));
            form.submit();
        }
        webDriver.get(linkedinUrl);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        List<String> skills =  wait.until(webDriver ->
                Arrays.stream(
                        webDriver.findElement(By.cssSelector("#skills+div+div"))
                                .getText().split("\n")).distinct().toList());
        return skills;
    }
}
