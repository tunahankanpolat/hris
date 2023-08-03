package obss.hris.business.abstracts;

import obss.hris.model.entity.Blacklist;
import obss.hris.model.entity.Candidate;
import obss.hris.model.entity.JobApplication;
import obss.hris.model.request.BanRequest;

import java.util.List;

public interface BlacklistService {
    void banCandidate(BanRequest banRequest);
}
