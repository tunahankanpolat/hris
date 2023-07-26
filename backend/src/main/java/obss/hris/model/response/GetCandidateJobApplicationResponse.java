package obss.hris.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import obss.hris.model.entity.JobApplicationStatus;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCandidateJobApplicationResponse {
    private Long jobApplicationId;
    private JobApplicationStatus status;
    private Date applicationTime;
    private Long jobPostId;
    private String code;
    private String title;
}
