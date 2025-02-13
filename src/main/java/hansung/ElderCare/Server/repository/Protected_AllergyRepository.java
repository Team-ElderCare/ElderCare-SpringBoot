package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Protected_Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Protected_AllergyRepository extends JpaRepository<Protected_Allergy, Long> {
}
