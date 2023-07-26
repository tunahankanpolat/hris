package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.CandidateNotFoundException;
import obss.hris.model.entity.Candidate;
import obss.hris.model.response.GetCandidateResponse;
import obss.hris.repository.CandidateRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private ModelMapperService modelMapperService;
    private CandidateRepository candidateRepository;

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
}
