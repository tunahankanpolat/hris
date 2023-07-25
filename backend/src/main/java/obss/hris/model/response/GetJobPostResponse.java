package obss.hris.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetJobPostResponse {
    private String code;
    private String title;
    private String description;
    private Date activationTime;
    private Date closureTime;
    private boolean isActive;
}
