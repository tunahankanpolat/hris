package obss.hris.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "job_posts")
@NoArgsConstructor
@AllArgsConstructor
public class JobPost {
    @Id
    @Column(name = "job_post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String code;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Column(name = "activation_time")
    private Date activationTime;

    @NotNull
    @Column(name = "closure_time")
    private Date closureTime;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "human_resource_id")
    private HumanResource humanResource;
}