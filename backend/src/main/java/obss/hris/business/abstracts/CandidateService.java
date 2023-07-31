package obss.hris.business.abstracts;

import obss.hris.model.entity.Candidate;
import obss.hris.model.response.GetCandidateResponse;
import org.springframework.security.core.Authentication;

public interface CandidateService {

    Candidate createCandidate(Candidate candidate);

    Candidate getCandidateByLinkedinId(String linkedinId);

    GetCandidateResponse getCandidateByIdForRequest(Long candidateId);

    Candidate getCandidateById(Long candidateId);

    String createCandidateIfNoExistAndScrapeSkills(String linkedinUrl);

    String getToken(Authentication authentication);
}
