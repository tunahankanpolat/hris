package obss.hris.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetJobPostResponse {
    private Long jobPostId;
    private String code;
    private String title;
    private String company;
    private String location;
    private String description;
    private Date activationTime;
    private Date closureTime;
    private boolean isActive;
    private List<String> requiredSkills;
}
