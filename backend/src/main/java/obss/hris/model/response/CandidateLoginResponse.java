package obss.hris.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateLoginResponse {
    private Long candidateId;
    private String token;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePicture;
    private String about;
    private List<String> skills;
}
