package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Protected_Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Protected_VaccineRepository extends JpaRepository<Protected_Vaccine, Long> {

    @Query("select v.name from Protected_Vaccine pv JOIN pv.vaccine v where pv.Protected.id = :id")
    List<String> findVaccineNamesByProtectedId(@Param("id") Long protectedId);
}
