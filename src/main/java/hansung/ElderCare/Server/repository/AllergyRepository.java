package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    Optional<Allergy> findByAllergyName(String allergyName);
}
