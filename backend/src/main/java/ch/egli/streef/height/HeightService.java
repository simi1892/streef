package ch.egli.streef.height;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class HeightService {

    private final RestClient restClient;

    public HeightService(
            RestClient.Builder restClientBuilder,
            @Value("${geo.admin.api.height-url}") String heightApiUrl) {
        this.restClient = restClientBuilder.baseUrl(heightApiUrl).build();
    }

    /**
     * Get height information for a given coordinate
     * 
     * @param latitude  WGS84 latitude
     * @param longitude WGS84 longitude
     * @return height in meters, or null if not available
     */
    public Double getHeightForCoordinate(double latitude, double longitude) {
        // Convert WGS84 to Swiss LV95 coordinates
        // This is a simplified approach.
        // TODO: For production, use a proper coordinate transformation library
        Coordinate coordinate = convertWGS84ToLV95(latitude, longitude);
        log.info("Requesting with easting {} and northing {}", coordinate.easting, coordinate.northing);

        HeightResponseDto response = restClient.get()
            .uri("/{easting}&{northing}", coordinate.easting, coordinate.northing)
            .retrieve()
            .body(HeightResponseDto.class);
        
        log.info("Receiving reponse {}", response);

        return response != null ? response.height() : null;
    }

    /**
     * Simple conversion from WGS84 to LV95
     * For production use, consider using a proper library like proj4j
     */
    private Coordinate convertWGS84ToLV95(double latitude, double longitude) {
        // This is a simplified approximation - for production code, use a proper
        // library
        // Adapted from
        // https://www.swisstopo.admin.ch/en/maps-data-online/calculation-services.html

        // Convert degrees to seconds
        double lat = latitude * 3600;
        double lon = longitude * 3600;

        // Calculate auxiliary values (difference in longitude from Bern in 10000")
        double lat_aux = (lat - 169028.66) / 10000;
        double lon_aux = (lon - 26782.5) / 10000;

        // Calculate E (easting) - simplified formula
        double x = 2600072.37
                + 211455.93 * lon_aux
                - 10938.51 * lon_aux * lat_aux
                - 0.36 * lon_aux * Math.pow(lat_aux, 2)
                - 44.54 * Math.pow(lon_aux, 3);

        // Calculate N (northing) - simplified formula
        double y = 1200147.07
                + 308807.95 * lat_aux
                + 3745.25 * Math.pow(lon_aux, 2)
                + 76.63 * Math.pow(lat_aux, 2)
                - 194.56 * Math.pow(lon_aux, 2) * lat_aux
                + 119.79 * Math.pow(lat_aux, 3);

        return new Coordinate(x, y);
    }

    private record Coordinate(double easting, double northing) {
    }
}