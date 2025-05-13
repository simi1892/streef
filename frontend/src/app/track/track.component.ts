import { Component, OnInit } from '@angular/core';
import { inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TrackDto } from './track.dto';
import { AsyncPipe } from '@angular/common';
import { TrackService } from './track-service';

@Component({
  selector: 'app-track',
  imports: [AsyncPipe],
  templateUrl: './track.component.html'
})
export class TrackComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly trackService = inject(TrackService)

  track$!: Observable<TrackDto>;

  constructor() {
    // TODO Handle garbage params
    const trackId = this.route.snapshot.paramMap.get('id');
    if (trackId) {
      this.track$ = this.trackService.byId(+trackId);
    } else {
      // TODO
      console.error('Handle me')
    }
  }
}
