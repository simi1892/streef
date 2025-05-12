package ch.egli.streef.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;


@RestController
@RequestMapping("/tracks")
@Log4j2
public class TrackController {

    private final TrackService trackService;
    
    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TrackDto> getTrackById(@PathVariable Long id) {
        try {
            TrackDto track = trackService.getTrackById(id);
            log.info("Retrieved track with ID: {} containing {} points", id, 
                    track.getPoints() != null ? track.getPoints().size() : 0);
            return ResponseEntity.ok(track);
        } catch (EntityNotFoundException e) {
            log.warn("Track with ID {} not found", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving track with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<TrackDto> createTrack(@RequestBody TrackDto trackDto) {
        try {
            TrackDto createdTrack = trackService.createTrack(trackDto);
            log.info("Created track with ID: {}", createdTrack.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTrack);
        } catch (Exception e) {
            log.error("Error creating track", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
        try {
            trackService.deleteTrack(id);
            log.info("Deleted track with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            log.warn("Cannot delete - track with ID {} not found", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting track with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}