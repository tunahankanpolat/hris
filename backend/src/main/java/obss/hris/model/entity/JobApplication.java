package obss.hris.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Entity
@Data
@Table(name = "job_applications")
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {
    @Id
    @Column(name = "job_application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private JobApplicationStatus status = JobApplicationStatus.PROCESSING;

    @Column(name = "application_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date applicationTime;

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
