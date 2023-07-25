package obss.hris.business.abstracts;

import obss.hris.model.entity.JobPost;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.UpdateJobPostRequest;
import obss.hris.model.response.GetJobPostResponse;

import java.util.List;

public interface JobPostService {
    List<GetJobPostResponse> getJobPosts();

    List<GetJobPostResponse> jobPostsByPage(int page, int size);

    GetJobPostResponse getJobPost(Long jobPostId);

    List<GetJobPostResponse> getJobPostsByCreator(String userName);

    List<GetJobPostResponse> getJobPostsByCreatorByPage(String userName, int page, int size);

    JobPost createJobPost(CreateJobPostRequest jobPost);

    JobPost updateJobPost(UpdateJobPostRequest jobPost);

    void deleteJobPost(Long jobPostId);
}
