package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.model.response.GetCandidateResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private ModelMapperService modelMapperService;

    @Override
    public GetCandidateResponse getCandidate(OAuth2User oauth2User) {
        Map<String, Object> map = oauth2User.getAttributes();
        GetCandidateResponse getCandidateResponse  = this.modelMapperService.forResponse().map(map, GetCandidateResponse.class);
        return getCandidateResponse;
    }
}
