package obss.hris.business.abstracts;

import jakarta.servlet.http.HttpServletRequest;
import obss.hris.model.entity.Candidate;
import obss.hris.model.response.CandidateLoginResponse;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.model.response.GetCandidateResponse;
import obss.hris.model.response.LoginResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CandidateService {

    Candidate createCandidate(Candidate candidate);

    Candidate getCandidateByLinkedinId(String linkedinId);

    GetCandidateResponse getCandidateByIdForRequest(Long candidateId);

    Candidate getCandidateById(Long candidateId);

    LoginResponse scrapeSkillsAndCreateCandidateIfNotExist(String linkedinUrl, HttpServletRequest request);

    LoginResponse login(HttpServletRequest request);

    void setCandidateAsBanned(Candidate candidate);

    List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(Long candidateId, int page, int size);
}
