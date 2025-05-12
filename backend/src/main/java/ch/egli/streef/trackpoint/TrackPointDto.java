package ch.egli.streef.trackpoint;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TrackPointDto {
    private Long id;
    private Long trackId;
    private double latitude;
    private double longitude;
    private double height;
    private LocalDateTime timestamp;
    private LocalDateTime createdAt;
}
