import { WebPlugin } from '@capacitor/core';

import type { backgroundHttpPlugin } from './definitions';

export class backgroundHttpWeb extends WebPlugin implements backgroundHttpPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
