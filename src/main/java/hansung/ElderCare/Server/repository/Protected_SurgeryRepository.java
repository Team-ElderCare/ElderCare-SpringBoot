package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Protected_Surgery;
import hansung.ElderCare.Server.domain.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Protected_SurgeryRepository extends JpaRepository<Protected_Surgery, Long> {
}
