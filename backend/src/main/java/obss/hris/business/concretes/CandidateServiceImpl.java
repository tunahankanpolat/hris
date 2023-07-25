package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.model.response.GetCandidateResponse;
import obss.hris.repository.CandidateRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private ModelMapperService modelMapperService;
    private CandidateRepository candidateRepository;

    @Override
    public GetCandidateResponse getCandidate(OAuth2User oauth2User) {
        Map<String, Object> map = oauth2User.getAttributes();
        return this.modelMapperService.forResponse().map(map, GetCandidateResponse.class);
    }
}
