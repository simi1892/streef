package ch.egli.streef.run;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ch.egli.streef.run_point.RunPointDto;
import lombok.Data;

@Data
public class RunDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<RunPointDto> points = new ArrayList<>();
    private String notes;
    private double distanceKm;
    private double avgPaceMinPerKm;
    private LocalDateTime createdAt;
}
