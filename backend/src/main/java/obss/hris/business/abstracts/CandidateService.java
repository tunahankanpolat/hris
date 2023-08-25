package obss.hris.business.abstracts;

import obss.hris.model.entity.Candidate;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.model.response.GetCandidateResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;

public interface CandidateService {
    void createCandidateIfNotExist(OAuth2User oauth2User);
    String logout(OAuth2AuthorizedClient authorizedClient);

    Candidate getCandidateByLinkedinId(String linkedinId);

    GetCandidateResponse getCandidateByIdForRequest(Long candidateId);

    Candidate getCandidateById(Long candidateId);

    String scrapeLinkedinProfile(String linkedinUrl, OAuth2User oauth2User);

    void setCandidateAsBanned(Candidate candidate);

    List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(Long candidateId, int page, int size);
}
