package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.BlacklistService;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.core.util.MailService;
import obss.hris.exception.CandidateAlreadyBannedException;
import obss.hris.model.entity.Blacklist;
import obss.hris.model.entity.Candidate;
import obss.hris.model.entity.JobApplicationStatus;
import obss.hris.model.request.BanRequest;
import obss.hris.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {
    private BlacklistRepository blacklistRepository;
    private JobApplicationService jobApplicationService;
    private CandidateService candidateService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private MailService mailService;


    @Override
    public void banCandidate(BanRequest banRequest) {
        Candidate candidate = candidateService.getCandidateById(banRequest.getId());
        if(candidate.isBanned())
            throw new CandidateAlreadyBannedException();
        jobApplicationService.batchUpdateStatus(candidate.getJobApplications(), JobApplicationStatus.REJECTED);
        Blacklist blacklist = new Blacklist();
        blacklist.setCandidate(candidate);
        blacklist.setReason(banRequest.getReason());
        blacklistRepository.save(blacklist);
        candidateService.setCandidateAsBanned(candidate);
        sendMailWithNewThread(candidate.getEmail(), banRequest.getReason());
    }

    private void sendMailWithNewThread(String to, String banReason){
        Runnable mailSendingTask = () -> {
            mailService.sendMailForCandidateBan(to, banReason);
        };
        executorService.submit(mailSendingTask);
    }
}
