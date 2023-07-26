package obss.hris.repository;

import obss.hris.model.entity.JobApplication;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
    List<JobApplication> findAllByCandidate_CandidateId(Long candidateId, Pageable pageable);

}
