package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    //해달 날짜 00시 ~ 24시 사이에 있는 활동기록을 리스트형태로 반환
    @Query("SELECT a FROM Activity a WHERE a.protectedPerson.id = :protectedId AND a.time BETWEEN :startTime AND :endTime ORDER BY a.time")
    public Optional<List<Activity>> findByProtectedIdAndTimeBetween(Long protectedId, LocalDateTime startTime, LocalDateTime endTime);

}
