package obss.scrape.service;

import obss.scrape.dto.CandidateScrapeResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Service
public class ScrapeService {
    @Value("${linkedin.login-url}")
    private String linkedinLoginUrl;

    @Value("${linkedin.account-email}")
    private String linkedinAccountEmail;

    @Value("${linkedin.account-password}")
    private String linkedinAccountPassword;

    private WebDriver webDriver;

    public ScrapeService(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public CandidateScrapeResponse scrapeSkills(String linkedinUrl) {
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
        By skillsLocator = By.cssSelector("#skills+div+div a[data-field=\"skill_card_skill_topic\"]");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(skillsLocator));
        try{
            List<String> skills =  wait.until(webDriver ->
                            webDriver.findElements(skillsLocator)
                                    .parallelStream().map(e -> e.getText().split("\n")[0]).toList());
            String about =  wait.until(webDriver ->
                    webDriver.findElement(By.cssSelector("#about+div+div")).getText());
            return new CandidateScrapeResponse(skills,about);
        }catch (Exception e){
            return new CandidateScrapeResponse();
        }
    }
}
