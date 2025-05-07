package ch.egli.streef.run;

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
@RequestMapping("/runs")
@Log4j2
public class RunController {

    private final RunService runService;
    
    @Autowired
    public RunController(RunService runService) {
        this.runService = runService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RunDto> getRunById(@PathVariable Long id) {
        try {
            RunDto run = runService.getRunById(id);
            log.info("Retrieved run with ID: {} containing {} points", id, 
                    run.getPoints() != null ? run.getPoints().size() : 0);
            return ResponseEntity.ok(run);
        } catch (EntityNotFoundException e) {
            log.warn("Run with ID {} not found", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving run with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<RunDto> createRun(@RequestBody RunDto runDto) {
        try {
            RunDto createdRun = runService.createRun(runDto);
            log.info("Created run with ID: {}", createdRun.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRun);
        } catch (Exception e) {
            log.error("Error creating run", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRun(@PathVariable Long id) {
        try {
            runService.deleteRun(id);
            log.info("Deleted run with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            log.warn("Cannot delete - run with ID {} not found", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting run with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}