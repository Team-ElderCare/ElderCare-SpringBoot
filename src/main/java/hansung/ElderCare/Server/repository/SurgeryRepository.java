package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurgeryRepository extends JpaRepository<Surgery, Long> {
    Optional<Surgery> findByName(String name);
}
