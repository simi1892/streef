package ch.egli.streef.track;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.egli.streef.trackpoint.TrackPointEntity;
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
@Table(name = "T_TRACK")
@Getter
@Setter
public class TrackEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    @OneToMany(
        mappedBy = "track", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true, 
        fetch = FetchType.LAZY
    )
    @OrderBy("timestamp ASC")
    private List<TrackPointEntity> points = new ArrayList<>();
    
    private String notes;
    private double distanceKm;
    private double avgPaceMinPerKm;
    private LocalDateTime createdAt;
    
    // Helper methods to manage bidirectional relationship
    public void addPoint(TrackPointEntity point) {
        points.add(point);
        point.setTrack(this);
    }
    
    public void removePoint(TrackPointEntity point) {
        points.remove(point);
        point.setTrack(null);
    }
}