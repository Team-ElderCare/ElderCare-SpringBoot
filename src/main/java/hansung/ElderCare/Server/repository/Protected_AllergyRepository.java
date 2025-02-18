package hansung.ElderCare.Server.repository;

import hansung.ElderCare.Server.domain.Allergy;
import hansung.ElderCare.Server.domain.Protected_Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Protected_AllergyRepository extends JpaRepository<Protected_Allergy, Long> {

    @Query("select a.allergyName from Protected_Allergy pa JOIN pa.allergy a where pa.Protected.id = :id")
    List<String> findAllergyNamesByProtectedId(@Param("id") Long protectedId);

    // update 및 delete 쿼리 작성 시 @Modifying 어노테이션 필요
    @Modifying
    @Query("delete from Protected_Allergy pa where pa.Protected.id = :protectedId")
    void deleteByProtectedId(@Param("protectedId")Long protectedId);
}
