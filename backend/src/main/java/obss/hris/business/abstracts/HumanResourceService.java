package obss.hris.business.abstracts;

import jakarta.servlet.http.HttpServletRequest;
import obss.hris.model.entity.HumanResource;
import obss.hris.model.entity.JobApplicationStatus;
import obss.hris.model.request.HumanResourceLoginRequest;
import obss.hris.model.response.GetHumanResourceResponse;
import obss.hris.model.response.GetJobPostApplicationResponse;
import obss.hris.model.response.LoginResponse;

import java.util.List;

public interface HumanResourceService {
    HumanResource createHumanResource(HumanResource humanResource);
    HumanResource getByUserName(String userName);
    GetHumanResourceResponse getByUserNameForRequest(String userName);
    LoginResponse login(HumanResourceLoginRequest loginRequest, HttpServletRequest request);
    List<GetJobPostApplicationResponse> getJobPostApplicationsByPage(Long jobPostId, int page, int size, JobApplicationStatus jobApplicationStatus, String searchKeyword);
}
