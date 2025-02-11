package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    // 이메일로 User Entity 가져오기
    Optional<User> findByEmail(String email);
}
