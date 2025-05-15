import { Component, Input, OnInit, inject } from '@angular/core';
import { catchError, Observable, Subscription, tap } from 'rxjs';
import { TrackDto } from './track.dto';
import { AsyncPipe } from '@angular/common';
import { TrackService } from './track-service';
import * as L from 'leaflet';

@Component({
  selector: 'app-track',
  imports: [AsyncPipe],
  templateUrl: './track.component.html',
  styleUrl: './track.component.scss'
})
export class TrackComponent implements OnInit {
  private readonly trackService = inject(TrackService)
  track$!: Observable<TrackDto>;
  private map: L.Map | null = null;

  @Input()
  set id(trackId: number) {
    const parsedId = Number(trackId);

    if (!Number.isInteger(parsedId) || parsedId <= 0) {
      console.error(`Invalid track ID:`, trackId);
      // TODO: what to do?
      return;
    }

    this.track$ = this.trackService.byId(parsedId).pipe(
      tap(track => {
        setTimeout(() => this.initMap(track), 100);
      })
    );
  }

  ngOnInit() {
    this.loadLeafletCSS();
  }

  private loadLeafletCSS() {
    if (!document.getElementById('leaflet-css')) {
      const link = document.createElement('link');
      link.id = 'leaflet-css';
      link.rel = 'stylesheet';
      link.href = 'https://unpkg.com/leaflet@1.9.4/dist/leaflet.css';
      link.integrity = 'sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=';
      link.crossOrigin = '';
      document.head.appendChild(link);
    }
  }

  private initMap(track: TrackDto) {
    if (!track.points || track.points.length === 0) {
      console.error('No track points available to display');
      return;
    }

    // Clean up previous map if exists
    if (this.map) {
      this.map.remove();
      this.map = null;
    }

    // Create map
    this.map = L.map('map');

    // Add OpenStreetMap tiles
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(this.map);

    // Create route line from track points
    const routePoints = track.points.map(point => [point.latitude, point.longitude] as L.LatLngExpression);

    // Create the polyline for the route
    const routeLine = L.polyline(routePoints, {
      color: '#3388ff',
      weight: 5,
      opacity: 0.7
    }).addTo(this.map);

    // TODO: replace marker at start- and endpoint with icon: https://leafletjs.com/examples/custom-icons/
    const startPoint = routePoints[0];
    L.marker(startPoint).addTo(this.map)
      .bindPopup('Start')
      .openPopup();

    // Add end marker if different from start
    if (routePoints.length > 1) {
      const endPoint = routePoints[routePoints.length - 1];
      L.marker(endPoint).addTo(this.map)
        .bindPopup('End');
    }

    // Fit map to route bounds
    this.map.fitBounds(routeLine.getBounds(), { padding: [30, 30] });
  }

  formatPace(paceMinPerKm: number): string {
    const minutes = Math.floor(paceMinPerKm);
    const seconds = Math.round((paceMinPerKm - minutes) * 60);
    return `${minutes}:${seconds.toString().padStart(2, '0')}`;
  }
}
