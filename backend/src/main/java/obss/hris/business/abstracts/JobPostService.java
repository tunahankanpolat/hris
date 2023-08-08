package obss.hris.business.abstracts;

import obss.hris.model.entity.JobPost;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.UpdateJobPostRequest;
import obss.hris.model.response.GetJobPostResponse;

import java.util.List;

public interface JobPostService {

    List<GetJobPostResponse> jobPostsByPage(int page, int size);

    GetJobPostResponse getJobPostByIdForRequest(Long jobPostId);

    JobPost getJobPostById(Long jobPostId);

    List<GetJobPostResponse> getJobPostsByCreatorByPage(String userName, int page, int size);

    JobPost createJobPost(String userName, CreateJobPostRequest jobPost);

    JobPost updateJobPost(UpdateJobPostRequest jobPost);

    void deleteJobPost(Long jobPostId);

    void applyToJobPost(Long jobPostId, Long candidateId);
}
