package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Protected_Surgery;
import hansung.ElderCare.Server.domain.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Protected_SurgeryRepository extends JpaRepository<Protected_Surgery, Long> {

    @Query("select s.name from Protected_Surgery ps JOIN ps.surgery s where ps.Protected.id = :id")
    List<String> findSurgeryNamesByProtectedId(@Param("id") Long protectedId);
}
