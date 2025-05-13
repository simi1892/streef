import { Component, Input, inject } from '@angular/core';
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
  private readonly trackService = inject(TrackService)
  track$!: Observable<TrackDto>;

  @Input()
  set id(trackId: number) {
    const parsedId = Number(trackId);

    if (!Number.isInteger(parsedId) || parsedId <= 0) {
      console.error(`Invalid track ID:`, trackId);
      // TODO: what to do?
      return;
    }

    this.track$ = this.trackService.byId(trackId);
  }
}
