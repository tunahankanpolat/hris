package obss.hris.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidates",
        indexes = {@Index(name = "candidate_linkedin_id_index", columnList = "linkedin_id")})

public class Candidate {
    @Id
    @Column(name = "candidate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    @Column(name = "linkedin_id")
    private String linkedinId;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    private String email;

    private String profilePicture;

    private String about;

    @ElementCollection
    private List<String> skills;

    @Column(name="is_banned", columnDefinition = "boolean default false")
    private boolean isBanned;
}
