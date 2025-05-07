package ch.egli.streef.run;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RunRepository extends JpaRepository<RunEntity, Long> {
    /**
     * Finds a run by its ID and eagerly fetches its points.
     * 
     * @param id the run ID
     * @return the run with its points
     */
    @Query("SELECT r FROM RunEntity r LEFT JOIN FETCH r.points WHERE r.id = :id")
    Optional<RunEntity> findByIdWithPoints(@Param("id") Long id);
}
