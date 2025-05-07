package ch.egli.streef.run_point;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RunPointDto {
    private Long id;
    private Long runId;
    private double latitude;
    private double longitude;
    private double height;
    private LocalDateTime timestamp;
    private LocalDateTime createdAt;
}
