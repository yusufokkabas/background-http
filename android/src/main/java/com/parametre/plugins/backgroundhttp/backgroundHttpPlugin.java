package com.parametre.plugins.backgroundhttp;

import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "BackgroundHttp")
public class BackgroundHttpPlugin extends Plugin {
    private static final String TAG = "BackgroundHttpPlugin";
    private BackgroundHttp implementation;

    @Override
    public void load() {
        Log.d(TAG, "Plugin loading");
        implementation = new BackgroundHttp();
        Log.d(TAG, "Plugin loaded successfully");
    }

    @PluginMethod
    public void echo(PluginCall call) {
        Log.d(TAG, "echo method called");
        String value = call.getString("value");
        Log.d(TAG, "echo value: " + value);

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
        Log.d(TAG, "echo method completed");
    }
    
    @PluginMethod
    public void sendLocationUpdate(PluginCall call) {
        Log.d(TAG, "sendLocationUpdate method called");
        
        String url = call.getString("url");
        String token = call.getString("token");
        String username = call.getString("username");
        Double latitude = call.getDouble("latitude");
        Double longitude = call.getDouble("longitude");
        
        // Get timestamp as integer (seconds since epoch)
        Integer timestampSec = call.getInt("timestamp");
        
        Log.d(TAG, "Raw timestamp value from call: " + call.getData().opt("timestamp"));
        Log.d(TAG, "Timestamp retrieved as Integer: " + timestampSec);
        
        // Convert to double for the implementation
        Double timestamp = timestampSec != null ? timestampSec.doubleValue() : null;
        
        Log.d(TAG, "Parameters received: url=" + url + 
              ", username=" + username + 
              ", latitude=" + (latitude != null ? latitude : "null") + 
              ", longitude=" + (longitude != null ? longitude : "null") + 
              ", timestamp=" + (timestamp != null ? timestamp : "null"));
        
        if (url == null || token == null || username == null || 
            latitude == null || longitude == null || timestamp == null) {
            Log.e(TAG, "Missing required parameters");
            call.reject("Missing required parameters");
            return;
        }
        
        // Save call for async response
        call.setKeepAlive(true);
        Log.d(TAG, "Call set to keep alive");
        
        try {
            Log.d(TAG, "About to call implementation.sendLocationUpdate");
            implementation.sendLocationUpdate(
                url,
                token,
                username,
                latitude,
                longitude,
                timestamp,
                new BackgroundHttp.LocationUpdateCallback() {
                    @Override
                    public void onSuccess(String message) {
                        Log.d(TAG, "LocationUpdateCallback.onSuccess: " + message);
                        JSObject result = new JSObject();
                        result.put("success", true);
                        result.put("message", message);
                        call.resolve(result);
                        Log.d(TAG, "Call resolved with success");
                    }
                    
                    @Override
                    public void onError(String errorMessage) {
                        Log.e(TAG, "LocationUpdateCallback.onError: " + errorMessage);
                        call.reject(errorMessage);
                        Log.e(TAG, "Call rejected with error");
                    }
                }
            );
            Log.d(TAG, "implementation.sendLocationUpdate called successfully");
        } catch (Exception e) {
            Log.e(TAG, "Exception in sendLocationUpdate", e);
            call.reject("Exception in sendLocationUpdate: " + e.getMessage());
        }
    }
}
