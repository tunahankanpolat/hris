package obss.hris.business.abstracts;

import obss.hris.model.request.BanRequest;

public interface BlacklistService {
    void banCandidate(BanRequest banRequest);
}
