package obss.hris.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import obss.hris.model.entity.JobApplicationStatus;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetJobPostApplicationResponse {
    private Long jobApplicationId;
    private JobApplicationStatus status;
    private Date applicationTime;
    private Long candidateId;
    private String firstName;
    private String lastName;
    private String profilePicture;
}
