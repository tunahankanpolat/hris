package obss.hris.business.abstracts;

import obss.hris.model.entity.HumanResource;

public interface HumanResourceService {
    HumanResource createHumanResource(HumanResource humanResource);

    HumanResource getByUserName(String userName);
}
