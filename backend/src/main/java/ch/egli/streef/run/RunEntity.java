package ch.egli.streef.run;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.egli.streef.run_point.RunPointEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_RUN")
@Getter
@Setter
public class RunEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    @OneToMany(
        mappedBy = "run", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true, 
        fetch = FetchType.LAZY
    )
    @OrderBy("timestamp ASC")
    private List<RunPointEntity> points = new ArrayList<>();
    
    private String notes;
    private double distanceKm;
    private double avgPaceMinPerKm;
    private LocalDateTime createdAt;
    
    // Helper methods to manage bidirectional relationship
    public void addPoint(RunPointEntity point) {
        points.add(point);
        point.setRun(this);
    }
    
    public void removePoint(RunPointEntity point) {
        points.remove(point);
        point.setRun(null);
    }
}