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
    // User 객체로 연관 테이블 조회
    Optional<UA_UD_UP> findByUser(User user);

    @Query("select u from UA_UD_UP u where u.user.id = :id")
    Optional<UA_UD_UP> findByUserId(@Param("id") Long id);
}
