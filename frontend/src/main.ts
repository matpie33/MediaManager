import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { RootModule } from './app/components/root-component/root.module';

platformBrowserDynamic().bootstrapModule(RootModule)
  .catch(err => console.error(err));
