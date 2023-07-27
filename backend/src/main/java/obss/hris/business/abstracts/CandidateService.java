package obss.hris.business.abstracts;

import obss.hris.model.entity.Candidate;
import obss.hris.model.response.GetCandidateResponse;

import java.util.List;

public interface CandidateService {

    GetCandidateResponse getCandidateFromSecurityContext();

    Candidate createCandidate(Candidate candidate);

    Candidate getCandidateByLinkedinId(String linkedinId);

    GetCandidateResponse getCandidateByIdForRequest(Long candidateId);

    Candidate getCandidateById(Long candidateId);

    List<String> scrapeSkillsFromLinkedin(String linkedinUrl);
}
