package ch.egli.streef.run_point;

import java.time.LocalDateTime;

import ch.egli.streef.run.RunEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_RUN_POINT")
@Getter
@Setter
public class RunPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "run_id", nullable = false)
    private RunEntity run;
    
    private double latitude;
    private double longitude;
    private double height;
    
    private LocalDateTime timestamp; // When this point was recorded
    private LocalDateTime createdAt; // When this record was created in the database
    
    // Equals and hashCode based on business key, not on ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RunPointEntity)) return false;
        
        RunPointEntity that = (RunPointEntity) o;
        
        if (id != null && that.id != null) {
            return id.equals(that.id);
        }
        
        return latitude == that.latitude && 
               longitude == that.longitude && 
               timestamp.equals(that.timestamp);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        if (id != null) {
            return id.hashCode();
        }
        result = prime * result + Double.hashCode(latitude);
        result = prime * result + Double.hashCode(longitude);
        result = prime * result + timestamp.hashCode();
        return result;
    }
}