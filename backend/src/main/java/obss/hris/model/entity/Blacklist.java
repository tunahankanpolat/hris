package obss.hris.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "blacklist")
@NoArgsConstructor
@AllArgsConstructor
public class Blacklist {
    @Id
    @Column(name = "blacklist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blacklistId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @NotNull
    @NotBlank
    private String reason;
}
