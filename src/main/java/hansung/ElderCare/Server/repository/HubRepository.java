package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Hub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HubRepository extends JpaRepository<Hub, Long> {

    @Query("SELECT h FROM Hub h LEFT JOIN FETCH h.user WHERE h.hubCode = :hubCode AND h.clientCode = :clientCode")
    Optional<Hub> findByHubCodeAndClientCodeWithUser(@Param("hubCode") String hubCode, @Param("clientCode") String clientCode);
}
