package ch.egli.streef.track;

import org.springframework.stereotype.Component;

import ch.egli.streef.trackpoint.TrackPointDto;
import ch.egli.streef.trackpoint.TrackPointEntity;

@Component
public class TrackMapper {
    
    public TrackDto toDto(TrackEntity entity) {
        if (entity == null) {
            return null;
        }
        
        TrackDto dto = new TrackDto();
        dto.setId(entity.getId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setNotes(entity.getNotes());
        dto.setDistanceKm(entity.getDistanceKm());
        dto.setAvgPaceMinPerKm(entity.getAvgPaceMinPerKm());
        dto.setCreatedAt(entity.getCreatedAt());
        
        // Map points if they exist
        if (entity.getPoints() != null) {
            dto.setPoints(
                entity.getPoints().stream()
                    .map(this::toPointDto)
                    .toList()
            );
        }
        
        return dto;
    }
    
    public TrackPointDto toPointDto(TrackPointEntity entity) {
        if (entity == null) {
            return null;
        }
        
        TrackPointDto dto = new TrackPointDto();
        dto.setId(entity.getId());
        dto.setTrackId(entity.getTrack() != null ? entity.getTrack().getId() : null);
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setHeight(entity.getHeight());
        dto.setTimestamp(entity.getTimestamp());
        dto.setCreatedAt(entity.getCreatedAt());
        
        return dto;
    }
    
    public TrackEntity toEntity(TrackDto dto) {
        if (dto == null) {
            return null;
        }
        
        TrackEntity entity = new TrackEntity();
        entity.setId(dto.getId());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setNotes(dto.getNotes());
        entity.setDistanceKm(dto.getDistanceKm());
        entity.setAvgPaceMinPerKm(dto.getAvgPaceMinPerKm());
        entity.setCreatedAt(dto.getCreatedAt());
        
        // Convert points and maintain bidirectional relationship
        if (dto.getPoints() != null) {
            dto.getPoints().forEach(pointDto -> {
                TrackPointEntity pointEntity = toPointEntity(pointDto);
                entity.addPoint(pointEntity); // This also sets the relationship
            });
        }
        
        return entity;
    }
    
    public TrackPointEntity toPointEntity(TrackPointDto dto) {
        if (dto == null) {
            return null;
        }
        
        TrackPointEntity entity = new TrackPointEntity();
        entity.setId(dto.getId());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setHeight(dto.getHeight());
        entity.setTimestamp(dto.getTimestamp());
        entity.setCreatedAt(dto.getCreatedAt());
        
        return entity;
    }
}