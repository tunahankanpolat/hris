package obss.hris.business.abstracts;

import obss.hris.model.response.GetCandidateResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CandidateService {

    GetCandidateResponse getCandidate(OAuth2User oauth2User);

}
