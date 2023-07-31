package obss.hris.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJobPostRequest {
    private Long jobPostId;
    private String title;
    private String description;
    private Date activationTime;
    private Date closureTime;
    private String company;
    private String location;
    private List<String> skills;
}
