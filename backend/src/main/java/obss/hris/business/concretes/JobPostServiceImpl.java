package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.business.abstracts.JobPostService;
import obss.hris.business.abstracts.LdapHumanResourceService;
import obss.hris.core.util.mapper.ModelMapperService;
import obss.hris.exception.JobPostNotFoundException;
import obss.hris.model.LdapPeople;
import obss.hris.model.entity.HumanResource;
import obss.hris.model.entity.JobApplication;
import obss.hris.model.entity.JobPost;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.UpdateJobPostRequest;
import obss.hris.model.response.GetJobPostApplicationResponse;
import obss.hris.model.response.GetJobPostResponse;
import obss.hris.repository.JobPostRepository;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class JobPostServiceImpl implements JobPostService {
    private JobPostRepository jobPostRepository;
    private ModelMapperService modelMapperService;

    private HumanResourceService humanResourceService;
    private LdapHumanResourceService ldapHumanResourceService;

    @Override
    public List<GetJobPostResponse> getJobPosts() {
        List<JobPost> jobPosts = jobPostRepository.findAll();
        return jobPosts.stream().map(jobPost ->
                modelMapperService.forResponse().map(jobPost, GetJobPostResponse.class)).toList();
    }

    @Override
    public List<GetJobPostApplicationResponse> getJobPostApplicationsByPage(Long jobPostId, int page, int size) {
        JobPost jobPost = this.getJobPostById(jobPostId);
        List<JobApplication> jobApplications = jobPost.getJobApplications();

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
    public List<GetJobPostResponse> jobPostsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<JobPost> jobPosts = jobPostRepository.findAll(pageable).getContent();
        return jobPosts.stream().map(jobPost ->
                modelMapperService.forResponse().map(jobPost, GetJobPostResponse.class)).toList();
    }

    @Override
    public GetJobPostResponse getJobPostByIdForRequest(Long jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId).orElse(null);
        if (jobPost == null) {
            throw new JobPostNotFoundException(jobPostId);
        }
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
    public List<GetJobPostResponse> getJobPostsByCreator(String userName) {
        HumanResource humanResource = humanResourceService.getByUserName(userName);
        List<JobPost> jobPosts = humanResource.getJobPosts();
        return jobPosts.stream().map(jobPost ->
                modelMapperService.forResponse().map(jobPost, GetJobPostResponse.class)).toList();
    }

    @Override
    public List<GetJobPostResponse> getJobPostsByCreatorByPage(String userName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<JobPost> jobPosts = jobPostRepository.findAllByHumanResource_UserName(userName, pageable);

        return jobPosts.stream().map(jobPost ->
                modelMapperService.forResponse().map(jobPost, GetJobPostResponse.class)).toList();
    }

    @Override
    public JobPost createJobPost(CreateJobPostRequest jobPost) {
        JobPost newJobPost = modelMapperService.forRequest().map(jobPost, JobPost.class);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        HumanResource humanResource = createHumanResourceIfNoExist(userName);

        if (jobPost.getActivationTime().compareTo(new Date()) <= 0) {
            newJobPost.setActive(true);
        }
        newJobPost.setHumanResource(humanResource);
        newJobPost.setCode(generateJobPostCode(jobPost));
        jobPostRepository.save(newJobPost);
        return newJobPost;
    }

    private HumanResource createHumanResourceIfNoExist(String userName) {
        HumanResource humanResource = humanResourceService.getByUserName(userName);
        if (humanResource == null) {
            LdapPeople ldapPeople = ldapHumanResourceService.getByUserName(userName);
            humanResource = modelMapperService.forCreate().map(ldapPeople, HumanResource.class);
            humanResource = humanResourceService.createHumanResource(humanResource);
        }
        return humanResource;
    }

    @Override
    public JobPost updateJobPost(UpdateJobPostRequest jobPost) {
        JobPost existingJobPost = jobPostRepository.findById(jobPost.getJobPostId()).orElse(null);
        if (existingJobPost == null) {
            throw new JobPostNotFoundException(jobPost.getJobPostId());
        }
        JobPost updatedJobPost = modelMapperService.forRequest().map(jobPost, JobPost.class);
        jobPostRepository.save(updatedJobPost);
        return updatedJobPost;
    }

    @Override
    public void deleteJobPost(Long jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId).orElse(null);
        if (jobPost == null) {
            throw new JobPostNotFoundException(jobPostId);
        }
        jobPostRepository.delete(jobPost);
    }

    private String generateJobPostCode(CreateJobPostRequest jobPostRequest) {
        return "JOB-" + jobPostRequest.getTitle().substring(0,3).toUpperCase()+"-" + LocalDate.now().getDayOfMonth();
    }
}
