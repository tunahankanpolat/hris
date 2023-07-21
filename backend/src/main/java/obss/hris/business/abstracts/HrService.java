package obss.hris.business.abstracts;

import obss.hris.model.request.LoginRequest;
import obss.hris.model.response.LoginResponse;

public interface HrService {
    LoginResponse login(LoginRequest loginRequest);
}
