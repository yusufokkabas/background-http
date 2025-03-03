import { WebPlugin } from '@capacitor/core';

import type { BackgroundHttpPlugin } from './definitions';

export class BackgroundHttpWeb extends WebPlugin implements BackgroundHttpPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async sendLocationUpdate(options: {
    url: string;
    token: string;
    username: string;
    latitude: number;
    longitude: number;
    timestamp: number;
  }): Promise<{ success: boolean; message: string }> {
    console.warn('BackgroundHttp.sendLocationUpdate is not implemented on web');
    
    // For testing purposes, we'll make a fetch request
    // In production, this won't work in the background on mobile
    try {
      const features = [
        {
          attributes: {
            username: options.username,
            y: options.latitude,
            x: options.longitude,
            time: options.timestamp,
          },
        },
      ];

      const formData = new FormData();
      formData.append('f', 'json');
      formData.append('features', JSON.stringify(features));

      const response = await fetch(options.url, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${options.token}`,
        },
        body: formData,
      });

      if (response.ok) {
        return {
          success: true,
          message: `Request successful with status: ${response.status}`,
        };
      } else {
        return {
          success: false,
          message: `Request failed with status: ${response.status}`,
        };
      }
    } catch (error) {
      return {
        success: false,
        message: `Error: ${error instanceof Error ? error.message : String(error)}`,
      };
    }
  }
}
