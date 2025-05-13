import { TrackPointDto } from './track-point.dto';

export interface TrackDto {
    id: number;
    startTime: string; // ISO string
    endTime: string;
    points: TrackPointDto[];
    notes: string;
    distanceKm: number;
    avgPaceMinPerKm: number;
    createdAt: string;
}
