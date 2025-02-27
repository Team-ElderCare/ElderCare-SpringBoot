package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Protected;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProtectedRepository extends JpaRepository<Protected, Long> {
    Optional<Protected> findByPhoneNumber(String phoneNumber);

    // ProtectedRepository에 추가
    @Query("SELECT u.Protected FROM UA_UD_UP u JOIN u.user usr JOIN Hub h ON h.user.id = usr.id " +
            "WHERE h.hubCode = :hubCode AND h.clientCode = :clientCode")
    Optional<Protected> findByHubCodeAndClientCode(@Param("hubCode") String hubCode,
                                                   @Param("clientCode") String clientCode);
}
