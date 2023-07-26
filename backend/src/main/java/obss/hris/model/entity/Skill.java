package obss.hris.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "skills")
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @Column(name = "skill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @NotNull
    @NotBlank
    @Column(name = "skill_name")
    private String skillName;

}
