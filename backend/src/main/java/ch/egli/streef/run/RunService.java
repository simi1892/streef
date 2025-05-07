package ch.egli.streef.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RunService {
    
    private final RunRepository runRepository;
    private final RunMapper runMapper;
    
    @Autowired
    public RunService(RunRepository runRepository, RunMapper runMapper) {
        this.runRepository = runRepository;
        this.runMapper = runMapper;
    }
    
    @Transactional(readOnly = true)
    public RunDto getRunById(Long id) {
        log.info("Fetching run with ID: {}", id);
        
        RunEntity run = runRepository.findByIdWithPoints(id)
            .orElseThrow(() -> new EntityNotFoundException("Run not found with ID: " + id));
        
        log.info("Found run with {} points", run.getPoints().size());
        
        return runMapper.toDto(run);
    }
    
    @Transactional
    public RunDto createRun(RunDto runDto) {
        RunEntity runEntity = runMapper.toEntity(runDto);
        runEntity = runRepository.save(runEntity);
        log.info("Created new run with ID: {}", runEntity.getId());
        return runMapper.toDto(runEntity);
    }
    
    @Transactional
    public void deleteRun(Long id) {
        if (!runRepository.existsById(id)) {
            throw new EntityNotFoundException("Run not found with ID: " + id);
        }
        
        runRepository.deleteById(id);
        log.info("Deleted run with ID: {}", id);
    }
}