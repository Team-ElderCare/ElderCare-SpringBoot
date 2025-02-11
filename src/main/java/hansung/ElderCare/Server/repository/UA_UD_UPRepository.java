package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Protected;
import hansung.ElderCare.Server.domain.UA_UD_UP;
import hansung.ElderCare.Server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UA_UD_UPRepository extends JpaRepository<UA_UD_UP, Long> {
    // userId로 UA_UD_UP 객체와 User 객체 join
    @Query("select u from UA_UD_UP u JOIN FETCH u.user where u.user.id = :id")
    Optional<UA_UD_UP> findByUserIdWithUser(@Param("id")Long userId);

    // userId로 UA_UD_UP 객체와 Protected 객체 join
    @Query("select u from UA_UD_UP u JOIN FETCH u.Protected where u.user.id = :id")
    Optional<UA_UD_UP> findByUserIdWithProtected(@Param("id") Long userId);
}
