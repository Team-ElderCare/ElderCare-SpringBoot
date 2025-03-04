package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.UA_UD_UP;
import hansung.ElderCare.Server.domain.enums.DeviceKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UA_UD_UPRepository extends JpaRepository<UA_UD_UP, Long> {
    // userId로 UA_UD_UP 객체와 User 객체 join
    @Query("select u from UA_UD_UP u JOIN FETCH u.user where u.user.id = :id")
    Optional<UA_UD_UP> findByUserIdWithUser(@Param("id")Long userId);

    // userId로 UA_UD_UP 객체와 Protected 객체 join
    @Query("SELECT u FROM UA_UD_UP u JOIN FETCH u.Protected WHERE u.user.id = :userId")
    Optional<UA_UD_UP> findByUserIdWithProtected(@Param("userId") Long userId);

    // 사용자 ID와 디바이스 종류로 존재 여부 확인
    boolean existsByUserIdAndDeviceDeviceKind(Long userId, DeviceKind deviceKind);

    // UA_UD_UPRepository.java에 추가
    @Query("SELECT COUNT(u) > 0 FROM UA_UD_UP u JOIN u.device d WHERE u.user.id = :userId AND d.deviceKind = :deviceKind")
    boolean existsByUserIdAndDeviceDeviceKindCustom(@Param("userId") Long userId, @Param("deviceKind") DeviceKind deviceKind);
}
