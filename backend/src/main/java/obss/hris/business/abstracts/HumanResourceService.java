package obss.hris.business.abstracts;

import obss.hris.model.entity.HumanResource;
import obss.hris.model.request.HumanResourceLoginRequest;
import obss.hris.model.response.GetHumanResourceResponse;
import obss.hris.model.response.HumanResourceLoginResponse;

public interface HumanResourceService {
    HumanResource createHumanResource(HumanResource humanResource);

    HumanResource getByUserName(String userName);

    GetHumanResourceResponse getByLdapUserName(String userName);
    HumanResourceLoginResponse login(HumanResourceLoginRequest loginRequest);
}
