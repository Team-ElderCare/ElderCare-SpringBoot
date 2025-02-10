package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Protected;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProtectedRepository extends JpaRepository<Protected, Long> {
    Optional<Protected> findByPhoneNumber(String phoneNumber);
}
