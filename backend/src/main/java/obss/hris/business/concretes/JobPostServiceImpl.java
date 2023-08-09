package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.JobApplicationService;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.CandidateBannedException;
import obss.hris.exception.JobPostNotActiveException;
import obss.hris.exception.JobPostNotFoundException;
import obss.hris.model.entity.Candidate;
import obss.hris.model.entity.HumanResource;
import obss.hris.model.entity.JobPost;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.UpdateJobPostRequest;
import obss.hris.model.response.GetJobPostResponse;
import obss.hris.repository.JobPostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class JobPostServiceImpl implements JobPostService {
    private JobPostRepository jobPostRepository;
    private ModelMapperService modelMapperService;
    private CandidateServiceImpl candidateService;
    private JobApplicationService jobApplicationService;
    private HumanResourceServiceImpl humanResourceService;

    @Override
    public List<GetJobPostResponse> jobPostsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<JobPost> jobPosts = jobPostRepository.findAllActiveJobPosts(pageable);
        return jobPosts.stream().map(jobPost ->
                modelMapperService.forResponse().map(jobPost, GetJobPostResponse.class)).toList();
    }

    @Override
    public GetJobPostResponse getJobPostByIdForRequest(Long jobPostId) {
        JobPost jobPost = getJobPostById(jobPostId);
        return modelMapperService.forResponse().map(jobPost, GetJobPostResponse.class);
    }

    @Override
    public JobPost getJobPostById(Long jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId).orElse(null);
        if (jobPost == null) {
            throw new JobPostNotFoundException(jobPostId);
        }
        return jobPost;
    }

    @Override
    public List<GetJobPostResponse> getJobPostsByCreatorByPage(String userName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<JobPost> jobPosts = jobPostRepository.findAllByHumanResource_UserName(userName, pageable);

        return jobPosts.stream().map(jobPost ->
                modelMapperService.forResponse().map(jobPost, GetJobPostResponse.class)).toList();
    }

    @Override
    public JobPost createJobPost(String userName, CreateJobPostRequest jobPost) {
        JobPost newJobPost = modelMapperService.forRequest().map(jobPost, JobPost.class);
        HumanResource humanResource = humanResourceService.getByUserName(userName);
        newJobPost.setHumanResource(humanResource);
        newJobPost.setCode(generateJobPostCode(jobPost));
        jobPostRepository.save(newJobPost);
        return newJobPost;
    }

    @Override
    public JobPost updateJobPost(UpdateJobPostRequest jobPost) {
        JobPost existingJobPost = getJobPostById(jobPost.getJobPostId());
        existingJobPost.updateJobPost(jobPost);
        if(jobPost.isActive() && !existingJobPost.getActive())
            existingJobPost.setClosureTime(Date.valueOf(LocalDate.now().plusDays(10)));
        else if(!jobPost.isActive() && existingJobPost.getActive())
            existingJobPost.setClosureTime(Date.valueOf(LocalDate.now()));

        jobPostRepository.save(existingJobPost);
        return existingJobPost;
    }

    @Override
    public void deleteJobPost(Long jobPostId) {
        JobPost jobPost = getJobPostById(jobPostId);
        jobPostRepository.delete(jobPost);
    }
    @Override
    public void applyToJobPost(Long jobPostId, Long candidateId) {
        Candidate candidate = candidateService.getCandidateById(candidateId);
        if(candidate.isBanned()){
            throw new CandidateBannedException();
        }
        JobPost jobPost = getJobPostById(jobPostId);
        if(!jobPost.getActive())
            throw new JobPostNotActiveException();
        jobApplicationService.createJobApplication(jobPost, candidate);
    }
    private String generateJobPostCode(CreateJobPostRequest jobPostRequest) {
        return "JOB-" + jobPostRequest.getTitle().substring(0,3).toUpperCase()+"-" + LocalDate.now().getDayOfMonth();
    }
}
