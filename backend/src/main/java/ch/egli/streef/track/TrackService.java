package ch.egli.streef.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TrackService {
    
    private final TrackRepository trackRepository;
    private final TrackMapper trackMapper;
    
    @Autowired
    public TrackService(TrackRepository trackRepository, TrackMapper trackMapper) {
        this.trackRepository = trackRepository;
        this.trackMapper = trackMapper;
    }
    
    @Transactional(readOnly = true)
    public TrackDto getTrackById(Long id) {
        log.info("Fetching track with ID: {}", id);
        
        TrackEntity track = trackRepository.findByIdWithPoints(id)
            .orElseThrow(() -> new EntityNotFoundException("Track not found with ID: " + id));
        
        log.info("Found track with {} points", track.getPoints().size());
        
        return trackMapper.toDto(track);
    }
    
    @Transactional
    public TrackDto createTrack(TrackDto trackDto) {
        TrackEntity trackEntity = trackMapper.toEntity(trackDto);
        trackEntity = trackRepository.save(trackEntity);
        log.info("Created new track with ID: {}", trackEntity.getId());
        return trackMapper.toDto(trackEntity);
    }
    
    @Transactional
    public void deleteTrack(Long id) {
        if (!trackRepository.existsById(id)) {
            throw new EntityNotFoundException("Track not found with ID: " + id);
        }
        
        trackRepository.deleteById(id);
        log.info("Deleted track with ID: {}", id);
    }
}