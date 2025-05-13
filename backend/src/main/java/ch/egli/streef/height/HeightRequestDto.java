package ch.egli.streef.height;

// Representation of Request from Endpoint "https://api3.geo.admin.ch/services/sdiservices.html#height"
public record HeightRequestDto(String easting, String northing, String sr) {
}
