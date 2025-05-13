package ch.egli.streef.track;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrackRepository extends JpaRepository<TrackEntity, Long> {
    /**
     * Finds a track by its ID and eagerly fetches its points.
     * 
     * @param id the track ID
     * @return the track with its points
     */
    @Query("SELECT t FROM TrackEntity t LEFT JOIN FETCH t.points WHERE t.id = :id")
    Optional<TrackEntity> findByIdWithPoints(@Param("id") Long id);
}
