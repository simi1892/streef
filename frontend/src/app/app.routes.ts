import { Routes } from '@angular/router';
import { TrackComponent } from './track/track.component';
import { FeedComponent } from './feed/feed.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

export const routes: Routes = [
    { path: 'feed', component: FeedComponent },
    { path: 'track', component: TrackComponent },
    { path: '', redirectTo: 'feed', pathMatch: 'full' },
    { path: '**', component: PageNotFoundComponent }
];
