package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    Optional<Vaccine> findByName(String name);
}
