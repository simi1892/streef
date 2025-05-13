import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { TrackDto } from "./track.dto";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class TrackService {
    private readonly httpClient = inject(HttpClient);
    private readonly baseUrl = 'http://localhost:8080/v1/tracks';

    public byId(id: number): Observable<TrackDto> {
        console.log('Make request with id', id);
        return this.httpClient.get<TrackDto>(`${this.baseUrl}/${id}`);
    }
}