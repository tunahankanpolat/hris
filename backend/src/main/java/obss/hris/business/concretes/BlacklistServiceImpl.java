package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.BlacklistService;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.model.entity.Blacklist;
import obss.hris.model.entity.Candidate;
import obss.hris.model.entity.JobApplicationStatus;
import obss.hris.model.request.BanRequest;
import obss.hris.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {
    private BlacklistRepository blacklistRepository;
    private JobApplicationService jobApplicationService;
    private CandidateService candidateService;

    @Override
    public void banCandidate(BanRequest banRequest) {
        Candidate candidate = candidateService.getCandidateById(banRequest.getId());
        jobApplicationService.batchUpdateStatus(candidate.getJobApplications(), JobApplicationStatus.REJECTED);
        Blacklist blacklist = new Blacklist();
        blacklist.setCandidate(candidate);
        blacklist.setReason(banRequest.getReason());
        blacklistRepository.save(blacklist);
        candidateService.setCandidateAsBanned(candidate);
    }
}
