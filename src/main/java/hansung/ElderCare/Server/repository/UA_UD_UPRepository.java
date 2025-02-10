package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.UA_UD_UP;
import hansung.ElderCare.Server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UA_UD_UPRepository extends JpaRepository<UA_UD_UP, Long> {
    // User 객체로 연관 테이블 조회
    Optional<UA_UD_UP> findByUser(User user);

}
