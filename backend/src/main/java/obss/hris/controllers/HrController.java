package obss.hris.controllers;

import obss.hris.business.abstracts.HrService;
import obss.hris.model.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hr/v1")
public class HrController {
    private HrService hrService;

    @Autowired
    public HrController(HrService hrService) {
        this.hrService=hrService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = hrService.login(loginRequest).getJwtToken();
        return  ResponseEntity.ok(token);
    }
}
