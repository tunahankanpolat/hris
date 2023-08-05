package obss.hris.business.concretes;

import co.elastic.clients.elasticsearch.ml.Job;
import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.CandidateService;
import obss.hris.business.abstracts.ElasticSearchService;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.CandidateNotFoundException;
import obss.hris.exception.JobPostNotFoundException;
import obss.hris.model.entity.*;
import obss.hris.model.request.CreateJobApplicationRequest;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.model.response.GetJobPostApplicationResponse;
import obss.hris.repository.JobApplicationRepository;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {
    private JobApplicationRepository jobApplicationRepository;
    private ModelMapperService modelMapperService;
    private ElasticSearchService elasticSearchService;
    @Override
    public JobApplication createJobApplication(JobPost jobPost, Candidate candidate) {
        JobApplication newJobApplication = new JobApplication();
        newJobApplication.setCandidate(candidate);
        newJobApplication.setJobPost(jobPost);
        this.jobApplicationRepository.save(newJobApplication);
        return newJobApplication;
    }

    @Override
    public List<GetJobPostApplicationResponse> getJobPostApplicationsByPage(Long jobPostId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<JobApplication> jobApplications = jobApplicationRepository.findAllByJobPost_JobPostId(jobPostId, pageable);
        return getMappedJobApplications(jobApplications);
    }

    @Override
    public List<GetJobPostApplicationResponse> getJobPostApplicationsByPageByStatus(Long jobPostId, int page, int size, JobApplicationStatus jobApplicationStatus) {
        Pageable pageable = PageRequest.of(page, size);
        List<JobApplication> jobApplications = jobApplicationRepository.findAllByJobPost_JobPostIdAndStatus(jobPostId, jobApplicationStatus, pageable);
        return getMappedJobApplications(jobApplications);
    }
    @Override
    public List<GetJobPostApplicationResponse> getJobPostApplicationsByPageByStatusBySearchKeyword(Long jobPostId, int page, int size, JobApplicationStatus jobApplicationStatus, String searchKeyword) {
        List<Long> candidateIds = getCandidateIdAppliedToJobPost(jobPostId);
        List<ElkCandidate> elkCandidates = elasticSearchService.searchOnCandidateForJobPost
                (candidateIds,searchKeyword, page, size);

        List<JobApplication> jobApplications = elkCandidates.stream().map(
                elkCandidate -> jobApplicationRepository.findAllByJobPost_JobPostIdAndCandidate_CandidateIdAndStatus
                        (jobPostId, elkCandidate.getCandidateId(), jobApplicationStatus)).toList();
        return getMappedJobApplications(jobApplications);
    }
    @Override
    public List<GetJobPostApplicationResponse> getJobPostApplicationsByPageBySearchKeyword(Long jobPostId, int page, int size, String searchKeyword) {
        List<Long> candidateIds = getCandidateIdAppliedToJobPost(jobPostId);
        List<ElkCandidate> elkCandidates = elasticSearchService.searchOnCandidateForJobPost(candidateIds,searchKeyword, page, size);
        List<JobApplication> jobApplications = elkCandidates.stream().map(
                elkCandidate -> jobApplicationRepository.findAllByJobPost_JobPostIdAndCandidate_CandidateId
                        (jobPostId, elkCandidate.getCandidateId())).toList();
        return getMappedJobApplications(jobApplications);
    }
    private List<GetJobPostApplicationResponse> getMappedJobApplications(List<JobApplication> jobApplications) {
        if (jobApplications == null) {
            return Collections.emptyList();
        }

        List<GetJobPostApplicationResponse> mappedResponses = new ArrayList<>();

        for (JobApplication jobApplication : jobApplications) {
            if (jobApplication != null && jobApplication.getCandidate() != null) {
                GetJobPostApplicationResponse response = modelMapperService.forResponse().map(jobApplication, GetJobPostApplicationResponse.class);
                response.setCandidateId(jobApplication.getCandidate().getCandidateId());
                mappedResponses.add(response);
            }
        }

        return mappedResponses;
    }

    @Override
    public List<JobApplication> getCandidateJobApplications(Long candidateId) {
        return jobApplicationRepository.findAllByCandidate_CandidateId(candidateId);
    }
    @Override
    public void batchUpdateStatus(List<JobApplication> jobApplications, JobApplicationStatus jobApplicationStatus) {
        jobApplications.forEach(jobApplication ->
            jobApplication.setStatus(jobApplicationStatus));
        jobApplicationRepository.saveAll(jobApplications);
    }
    @Override
    public List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(Long candidateId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<JobApplication> jobApplications = jobApplicationRepository.findAllByCandidate_CandidateId(candidateId, pageable);

        return jobApplications.stream().map(jobApplication ->
                modelMapperService.forResponse().map(jobApplication, GetCandidateJobApplicationResponse.class)).toList();
    }

    private List<Long> getCandidateIdAppliedToJobPost(Long jobPostId){
        List<JobApplication> jobApplications = jobApplicationRepository.findByJobPost_JobPostId(jobPostId);
        return jobApplications.stream().map(jobApplication -> jobApplication.getCandidate().getCandidateId()).toList();
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
