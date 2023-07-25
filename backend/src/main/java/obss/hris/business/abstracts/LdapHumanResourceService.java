package obss.hris.business.abstracts;

import obss.hris.model.LdapPeople;

public interface LdapHumanResourceService {
    LdapPeople getByUserName(String userName);
}
