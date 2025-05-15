import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

// TODO: Move project to UI dir in Spring?: https://github.com/spring-guides/tut-spring-security-and-angular-js/tree/main/oauth2

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
