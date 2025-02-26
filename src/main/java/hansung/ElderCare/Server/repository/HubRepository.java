package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Hub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HubRepository extends JpaRepository<Hub, Long> {

    Optional<Hub> findByHubCodeAndClientCode(String hubCode, String clientCode);
}
