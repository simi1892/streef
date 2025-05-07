package ch.egli.streef.run;

import org.springframework.stereotype.Component;

import ch.egli.streef.run_point.RunPointDto;
import ch.egli.streef.run_point.RunPointEntity;

@Component
public class RunMapper {
    
    public RunDto toDto(RunEntity entity) {
        if (entity == null) {
            return null;
        }
        
        RunDto dto = new RunDto();
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
    
    public RunPointDto toPointDto(RunPointEntity entity) {
        if (entity == null) {
            return null;
        }
        
        RunPointDto dto = new RunPointDto();
        dto.setId(entity.getId());
        dto.setRunId(entity.getRun() != null ? entity.getRun().getId() : null);
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setHeight(entity.getHeight());
        dto.setTimestamp(entity.getTimestamp());
        dto.setCreatedAt(entity.getCreatedAt());
        
        return dto;
    }
    
    public RunEntity toEntity(RunDto dto) {
        if (dto == null) {
            return null;
        }
        
        RunEntity entity = new RunEntity();
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
                RunPointEntity pointEntity = toPointEntity(pointDto);
                entity.addPoint(pointEntity); // This also sets the relationship
            });
        }
        
        return entity;
    }
    
    public RunPointEntity toPointEntity(RunPointDto dto) {
        if (dto == null) {
            return null;
        }
        
        RunPointEntity entity = new RunPointEntity();
        entity.setId(dto.getId());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setHeight(dto.getHeight());
        entity.setTimestamp(dto.getTimestamp());
        entity.setCreatedAt(dto.getCreatedAt());
        
        return entity;
    }
}