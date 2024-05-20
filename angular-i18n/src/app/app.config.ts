import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { provideTranslation } from './core/config/i18n/translate-loader.config';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    importProvidersFrom(TranslateModule.forRoot(provideTranslation())),
    provideHttpClient(),
  ]
};
