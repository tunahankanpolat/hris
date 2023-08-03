package obss.hris.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import obss.hris.model.entity.JobApplicationStatus;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCandidateJobApplicationResponse {
    private Long jobApplicationId;
    private JobApplicationStatus status;
    private Date applicationTime;
    private String firstName;
    private String lastName;
    private String profilePicture;

    private Long jobPostId;
    private String code;
    private String title;
    private String company;
    private String location;
    private String description;
    private List<String> requiredSkills;
    private boolean isActive;
}
