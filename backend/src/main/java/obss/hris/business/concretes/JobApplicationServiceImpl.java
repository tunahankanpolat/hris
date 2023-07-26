package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.JobPostNotFoundException;
import obss.hris.model.entity.Candidate;
import obss.hris.model.entity.JobApplication;
import obss.hris.model.entity.JobPost;
import obss.hris.model.request.CreateJobApplicationRequest;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.repository.JobApplicationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {
    private JobApplicationRepository jobApplicationRepository;
    private ModelMapperService modelMapperService;
    private CandidateService candidateService;
    private JobPostService jobPostService;

    @Override
    public JobApplication createJobApplication(CreateJobApplicationRequest jobApplication) {
        JobPost jobPost = jobPostService.getJobPostById(jobApplication.getJobPostId());
        if(jobPost == null) {
            throw new JobPostNotFoundException(jobApplication.getJobPostId());
        }
        OAuth2User oauth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long candidateId = Long.valueOf(oauth2User.getName());
        Candidate candidate = candidateService.getCandidateById(candidateId);
        JobApplication newJobApplication = this.modelMapperService.forCreate().map(jobApplication, JobApplication.class);
        newJobApplication.setCandidate(candidate);
        newJobApplication.setJobPost(jobPost);
        this.jobApplicationRepository.save(newJobApplication);
        return newJobApplication;
    }

    @Override
    public List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(Long candidateId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<JobApplication> jobApplications = jobApplicationRepository.findAllByCandidate_CandidateId(candidateId, pageable);

        return jobApplications.stream().map(jobApplication ->
                modelMapperService.forResponse().map(jobApplication, GetCandidateJobApplicationResponse.class)).toList();
    }

    @Override
    public List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        OAuth2User oauth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long candidateId = Long.valueOf(oauth2User.getName());
        List<JobApplication> jobApplications = jobApplicationRepository.findAllByCandidate_CandidateId(candidateId, pageable);

        return jobApplications.stream().map(jobApplication ->
                modelMapperService.forRequest().map(jobApplication, GetCandidateJobApplicationResponse.class)).toList();
    }
}
