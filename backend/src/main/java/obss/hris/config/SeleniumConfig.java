package obss.hris.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SeleniumConfig {
    @Bean
    WebDriver webDriver() {
        return new ChromeDriver();
    }
}
