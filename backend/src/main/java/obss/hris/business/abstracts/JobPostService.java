package obss.hris.business.abstracts;

import obss.hris.model.entity.JobPost;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.UpdateJobPostRequest;
import obss.hris.model.response.GetJobPostApplicationResponse;
import obss.hris.model.response.GetJobPostResponse;

import java.util.List;

public interface JobPostService {
    List<GetJobPostResponse> getJobPosts();

    List<GetJobPostApplicationResponse> getJobPostApplicationsByPage(Long jobPostId, int page, int size);

    List<GetJobPostResponse> jobPostsByPage(int page, int size);

    GetJobPostResponse getJobPostByIdForRequest(Long jobPostId);

    JobPost getJobPostById(Long jobPostId);

    List<GetJobPostResponse> getJobPostsByCreator(String userName);

    List<GetJobPostResponse> getJobPostsByCreatorByPage(String userName, int page, int size);

    JobPost createJobPost(CreateJobPostRequest jobPost);

    JobPost updateJobPost(UpdateJobPostRequest jobPost);

    void deleteJobPost(Long jobPostId);
}
