package obss.hris.business.abstracts;

import obss.hris.model.entity.JobApplication;
import obss.hris.model.request.CreateJobApplicationRequest;
import obss.hris.model.response.GetCandidateJobApplicationResponse;

import java.util.List;

public interface JobApplicationService {
    JobApplication createJobApplication(CreateJobApplicationRequest jobApplication);

    List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(Long candidateId, int page, int size);

    List<GetCandidateJobApplicationResponse> getCandidateJobApplicationsByPage(int page, int size);
}
