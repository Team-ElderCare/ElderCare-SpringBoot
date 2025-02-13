package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Protected_Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Protected_AllergyRepository extends JpaRepository<Protected_Allergy, Long> {

    @Query("select a.allergyName from Protected_Allergy pa JOIN pa.allergy a where pa.Protected.id = :id")
    List<String> findAllergyNamesByProtectedId(@Param("id") Long protectedId);

}
