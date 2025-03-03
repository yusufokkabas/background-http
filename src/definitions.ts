export interface BackgroundHttpPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  
  /**
   * Sends a location update to the server using native HTTP implementation
   * that works reliably in the background
   * 
   * @param options The location data and API details
   * @returns A promise that resolves with the result of the HTTP request
   */
  sendLocationUpdate(options: {
    url: string;
    token: string;
    username: string;
    latitude: number;
    longitude: number;
    timestamp: number;
  }): Promise<{ success: boolean; message: string }>;
}
