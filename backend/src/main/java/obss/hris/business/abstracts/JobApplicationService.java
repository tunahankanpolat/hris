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

    List<GetJobPostApplicationResponse> getJobPostApplicationsByPage(Long jobPostId, int page, int size, JobApplicationStatus jobApplicationStatus);
    List<JobApplication> getCandidateJobApplications(Long candidateId);
    public void batchUpdateStatus(List<JobApplication> jobApplications, JobApplicationStatus jobApplicationStatus);

}
