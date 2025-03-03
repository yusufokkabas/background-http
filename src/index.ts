import { registerPlugin } from '@capacitor/core';

import type { BackgroundHttpPlugin } from './definitions';

const BackgroundHttp = registerPlugin<BackgroundHttpPlugin>('BackgroundHttp', {
  web: () => import('./web').then((m) => new m.BackgroundHttpWeb()),
});

export * from './definitions';
export { BackgroundHttp };
