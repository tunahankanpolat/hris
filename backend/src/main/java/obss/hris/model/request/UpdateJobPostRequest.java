package obss.hris.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJobPostRequest {
    private Long id;
    private String title;
    private String description;
    private Date activationTime;
    private Date closureTime;
}
