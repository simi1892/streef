package ch.egli.streef.track;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.egli.streef.trackpoint.TrackPointDto;
import lombok.Data;

@Data
public class TrackDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<TrackPointDto> points = new ArrayList<>();
    private String notes;
    private double distanceKm;
    private double avgPaceMinPerKm;
    private LocalDateTime createdAt;
}
