export interface TrackPointDto {
    id: number;
    trackId: number;
    latitude: number;
    longitude: number;
    height?: number | null;
    timestamp: string; // ISO string (e.g., "2025-05-13T12:34:56")
    createdAt: string;
}
