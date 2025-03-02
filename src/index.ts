import { registerPlugin } from '@capacitor/core';

import type { backgroundHttpPlugin } from './definitions';

const backgroundHttp = registerPlugin<backgroundHttpPlugin>('backgroundHttp', {
  web: () => import('./web').then((m) => new m.backgroundHttpWeb()),
});

export * from './definitions';
export { backgroundHttp };
