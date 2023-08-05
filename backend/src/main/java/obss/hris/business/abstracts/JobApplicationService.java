package obss.hris.business.abstracts;

import obss.hris.model.entity.Candidate;
import obss.hris.model.entity.JobApplication;
import obss.hris.model.entity.JobApplicationStatus;
import obss.hris.model.entity.JobPost;
import obss.hris.model.request.CreateJobApplicationRequest;
import obss.hris.model.response.GetCandidateJobApplicationResponse;
import obss.hris.model.response.GetJobPostApplicationResponse;

import java.util.List;

public interface JobApplicationService {
    JobApplication createJobApplication(JobPost jobPost, Candidate candidate);

    List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(Long candidateId, int page, int size);

    void deleteJobApplication(Long jobApplicationId);

    void updateStatus(Long jobApplicationId, JobApplicationStatus jobApplicationStatus);
    List<GetJobPostApplicationResponse> getJobPostApplicationsByPageByStatusBySearchKeyword(Long jobPostId, int page, int size, JobApplicationStatus jobApplicationStatus, String searchKeyword);
    List<GetJobPostApplicationResponse> getJobPostApplicationsByPageBySearchKeyword(Long jobPostId, int page, int size, String searchKeyword);
    List<GetJobPostApplicationResponse> getJobPostApplicationsByPageByStatus(Long jobPostId, int page, int size, JobApplicationStatus jobApplicationStatus);
    List<GetJobPostApplicationResponse> getJobPostApplicationsByPage(Long jobPostId, int page, int size);
    List<JobApplication> getCandidateJobApplications(Long candidateId);
    void batchUpdateStatus(List<JobApplication> jobApplications, JobApplicationStatus jobApplicationStatus);

}
