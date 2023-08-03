package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.CandidateNotFoundException;
import obss.hris.exception.JobPostNotFoundException;
import obss.hris.model.entity.Candidate;
import obss.hris.model.entity.JobApplication;
import obss.hris.model.entity.JobApplicationStatus;
import obss.hris.model.entity.JobPost;
import obss.hris.model.request.CreateJobApplicationRequest;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.model.response.GetJobPostApplicationResponse;
import obss.hris.repository.JobApplicationRepository;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {
    private JobApplicationRepository jobApplicationRepository;
    private ModelMapperService modelMapperService;

    @Override
    public JobApplication createJobApplication(JobPost jobPost, Candidate candidate) {
        JobApplication newJobApplication = new JobApplication();
        newJobApplication.setCandidate(candidate);
        newJobApplication.setJobPost(jobPost);
        this.jobApplicationRepository.save(newJobApplication);
        return newJobApplication;
    }

    @Override
    public List<GetJobPostApplicationResponse> getJobPostApplicationsByPage(Long jobPostId, int page, int size, JobApplicationStatus jobApplicationStatus) {
        List<JobApplication> jobApplications;
        Pageable pageable = PageRequest.of(page, size);
        if(jobApplicationStatus == null){
            jobApplications = jobApplicationRepository.findAllByJobPost_JobPostId(jobPostId, pageable);
        }else{
            jobApplications = jobApplicationRepository.findAllByJobPost_JobPostIdAndStatus(jobPostId, jobApplicationStatus, pageable);
        }

        TypeMap<JobApplication, GetJobPostApplicationResponse> typeMap = this.modelMapperService.forResponse().getTypeMap(JobApplication.class, GetJobPostApplicationResponse.class);
        if(typeMap == null){
            typeMap = this.modelMapperService.forResponse().createTypeMap(JobApplication.class, GetJobPostApplicationResponse.class);

            typeMap.addMappings(
                    mapper -> mapper.map(src -> src.getCandidate().getCandidateId(), GetJobPostApplicationResponse::setCandidateId)
            );
        }

        return jobApplications.stream().map(jobApplication ->
                modelMapperService.forResponse().map(jobApplication, GetJobPostApplicationResponse.class)).toList();
    }

    @Override
    public List<JobApplication> getCandidateJobApplications(Long candidateId) {
        return jobApplicationRepository.findAllByCandidate_CandidateId(candidateId);
    }
    @Override
    public void batchUpdateStatus(List<JobApplication> jobApplications, JobApplicationStatus jobApplicationStatus) {
        jobApplications.forEach(jobApplication -> {
            jobApplication.setStatus(jobApplicationStatus);
        });
        jobApplicationRepository.saveAll(jobApplications);
    }
    @Override
    public List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(Long candidateId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<JobApplication> jobApplications = jobApplicationRepository.findAllByCandidate_CandidateId(candidateId, pageable);

        return jobApplications.stream().map(jobApplication ->
                modelMapperService.forResponse().map(jobApplication, GetCandidateJobApplicationResponse.class)).toList();
    }

    @Override
    public void deleteJobApplication(Long jobApplicationId) {
        jobApplicationRepository.deleteById(jobApplicationId);
    }

    @Override
    public void updateStatus(Long jobApplicationId, JobApplicationStatus jobApplicationStatus) {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId).orElseThrow(() -> new JobPostNotFoundException(jobApplicationId));
        jobApplication.setStatus(jobApplicationStatus);
        jobApplicationRepository.save(jobApplication);
    }
}
