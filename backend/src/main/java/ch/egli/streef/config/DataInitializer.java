package ch.egli.streef.config;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.egli.streef.track.TrackEntity;
import ch.egli.streef.track.TrackRepository;
import ch.egli.streef.trackpoint.TrackPointEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {
    private final TrackRepository trackRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            log.info("Initializing sample track data");
            
            // Check if we already have data
            if (trackRepository.count() > 0) {
                log.info("Data already exists, skipping initialization");
                return;
            }
            
            // Create a new track from Schulwiesstrasse 6 to Müliweiher in Steinmaur
            LocalDateTime startTime = LocalDateTime.now().minusDays(2).withHour(18).withMinute(30).withSecond(0);
            LocalDateTime endTime = startTime.plusMinutes(25); // 25 minute track
            
            // Calculate distance for this route (approximate)
            double distanceKm = 0.9; // ~900 meters from Schulwiesstrasse 6 to Müliweiher
            
            // Calculate duration in seconds
            long durationSeconds = ChronoUnit.SECONDS.between(startTime, endTime);
            
            // Calculate pace in minutes per kilometer
            double avgPaceMinPerKm = (durationSeconds / 60.0) / distanceKm;
            
            TrackEntity trackEntity = new TrackEntity();
            // Let the ID be generated automatically
            trackEntity.setStartTime(startTime);
            trackEntity.setEndTime(endTime);
            trackEntity.setNotes("Evening track from home to Müliweiher");
            trackEntity.setDistanceKm(distanceKm);
            trackEntity.setAvgPaceMinPerKm(avgPaceMinPerKm);
            trackEntity.setCreatedAt(startTime);
            
            // Create approximately 10 track points for the route
            // GPS coordinates for Schulwiesstrasse 6, Steinmaur to Müliweiher
            List<double[]> coordinatesList = new ArrayList<>();
            
            // Starting point: Schulwiesstrasse 6, Steinmaur
            coordinatesList.add(new double[]{47.5146, 8.4459});
            
            // Points along the route to Müliweiher
            coordinatesList.add(new double[]{47.5144, 8.4464}); // Moving east on Schulwiesstrasse
            coordinatesList.add(new double[]{47.5143, 8.4472}); // Continuing on Schulwiesstrasse
            coordinatesList.add(new double[]{47.5142, 8.4480}); // Approaching Dorfstrasse
            coordinatesList.add(new double[]{47.5139, 8.4486}); // Turning onto Dorfstrasse
            coordinatesList.add(new double[]{47.5135, 8.4492}); // Continuing on Dorfstrasse
            coordinatesList.add(new double[]{47.5131, 8.4499}); // Moving southeast
            coordinatesList.add(new double[]{47.5129, 8.4507}); // Approaching path to Müliweiher
            coordinatesList.add(new double[]{47.5126, 8.4515}); // On path to Müliweiher
            coordinatesList.add(new double[]{47.5122, 8.4519}); // Arriving at Müliweiher
            
            // Calculate time interval between points
            long trackDurationSeconds = ChronoUnit.SECONDS.between(startTime, endTime);
            long intervalSeconds = trackDurationSeconds / (coordinatesList.size() - 1);
            
            // Create and add all points using the proper bidirectional relationship
            for (int i = 0; i < coordinatesList.size(); i++) {
                TrackPointEntity point = new TrackPointEntity();
                // Let the ID be generated automatically
                point.setTimestamp(startTime.plusSeconds(i * intervalSeconds)); // Use timestamp for point's time
                point.setCreatedAt(LocalDateTime.now()); // Use current time for record creation
                point.setLatitude(coordinatesList.get(i)[0]);
                point.setLongitude(coordinatesList.get(i)[1]);
                // Set a mock elevation to demonstrate the field
                point.setHeight(450 + Math.random() * 10); // Random elevation between 450-460m
                
                // Use the helper method to maintain bidirectional relationship
                trackEntity.addPoint(point);
                
                log.debug("Created track point: {}", point);
            }
            
            // Save the track - cascade will save all points too
            TrackEntity savedTrack = trackRepository.save(trackEntity);
            
            log.info("Created track with ID: {} and {} points", savedTrack.getId(), savedTrack.getPoints().size());
            log.info("Data initialization complete");
        };
    }
}