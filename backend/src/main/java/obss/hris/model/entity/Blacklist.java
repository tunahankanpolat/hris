package obss.hris.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;



@Entity
@Data
@Table(name = "blacklist")
@NoArgsConstructor
@AllArgsConstructor
public class Blacklist {
    @Id
    @Column(name = "blacklist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @NotNull
    @NotBlank
    private String reason;
}
