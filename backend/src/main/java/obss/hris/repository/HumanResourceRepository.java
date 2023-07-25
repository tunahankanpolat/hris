package obss.hris.repository;

import obss.hris.model.entity.HumanResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanResourceRepository extends JpaRepository<HumanResource,Long> {
    HumanResource findByUserName(String userName);
}
