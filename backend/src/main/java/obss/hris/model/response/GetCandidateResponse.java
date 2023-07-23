package obss.hris.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCandidateResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String profilePicture;
    private String about;
}
