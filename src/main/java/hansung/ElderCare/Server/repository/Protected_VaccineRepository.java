package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Protected_Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Protected_VaccineRepository extends JpaRepository<Protected_Vaccine, Long> {
}
