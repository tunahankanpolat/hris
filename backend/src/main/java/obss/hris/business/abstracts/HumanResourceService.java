package obss.hris.business.abstracts;

import obss.hris.model.response.GetHumanResourceResponse;

public interface HumanResourceService {
    GetHumanResourceResponse getByUsername(String username);
}
