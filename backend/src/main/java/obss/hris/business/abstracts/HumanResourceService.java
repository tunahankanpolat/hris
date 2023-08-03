package obss.hris.business.abstracts;

import jakarta.servlet.http.HttpServletRequest;
import obss.hris.model.entity.HumanResource;
import obss.hris.model.entity.JobApplicationStatus;
import obss.hris.model.entity.JobPost;
import obss.hris.model.request.CreateJobPostRequest;
import obss.hris.model.request.HumanResourceLoginRequest;
import obss.hris.model.response.*;

import java.util.List;

public interface HumanResourceService {
    HumanResource createHumanResource(HumanResource humanResource);
    HumanResource getByUserName(String userName);
    GetHumanResourceResponse getByUserNameForRequest(String userName);
    LoginResponse login(HumanResourceLoginRequest loginRequest, HttpServletRequest request);
    List<GetJobPostApplicationResponse> getJobPostApplicationsByPage(Long jobPostId, int page, int size, JobApplicationStatus jobApplicationStatus);
    List<GetJobPostResponse> getJobPostsByPage(String userName, int page, int size);
}
