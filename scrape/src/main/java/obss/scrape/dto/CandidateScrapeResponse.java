package obss.scrape.dto;

import java.util.List;

public class CandidateScrapeResponse {
    private List<String> skills;
    private String about;

    public CandidateScrapeResponse(List<String> skills, String about) {
        this.skills = skills;
        this.about = about;
    }
    public CandidateScrapeResponse() {
    }
    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}