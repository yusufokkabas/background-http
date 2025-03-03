package com.parametre.plugins.backgroundhttp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BackgroundHttp {
    private static final String TAG = "BackgroundHttp";
    private OkHttpClient httpClient;

    public BackgroundHttp() {
        Log.d(TAG, "BackgroundHttp constructor called");
        httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
        Log.d(TAG, "OkHttpClient initialized");
    }

    public String echo(String value) {
        Log.i(TAG, "Echo method called with value: " + value);
        return value;
    }
    
    public interface LocationUpdateCallback {
        void onSuccess(String message);
        void onError(String errorMessage);
    }
    
    public void sendLocationUpdate(
            String url,
            String token,
            String username,
            double latitude,
            double longitude,
            double timestamp,
            LocationUpdateCallback callback) {
        
        Log.d(TAG, "sendLocationUpdate called with params: url=" + url + 
              ", username=" + username + 
              ", latitude=" + latitude + 
              ", longitude=" + longitude + 
              ", timestamp=" + timestamp);
        
        try {
            // Create features JSON
            JSONArray features = new JSONArray();
            JSONObject feature = new JSONObject();
            JSONObject attributes = new JSONObject();
            
            attributes.put("username", username);
            attributes.put("y", latitude);
            attributes.put("x", longitude);
            attributes.put("time", timestamp);
            
            feature.put("attributes", attributes);
            features.put(feature);
            
            String featuresJson = features.toString();
            Log.d(TAG, "Features JSON created: " + featuresJson);
            
            // Create form body
            FormBody.Builder formBuilder = new FormBody.Builder()
                .add("f", "json")
                .add("features", featuresJson);
            
            Log.d(TAG, "Form body created");
            
            // Create request
            Request request = new Request.Builder()
                .url(url)
                .post(formBuilder.build())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Bearer " + token)
                .build();
            
            Log.d(TAG, "Request created, about to execute");
            
            // Execute request
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "HTTP request failed", e);
                    Log.e(TAG, "Failure details: " + e.getMessage());
                    callback.onError("Request failed: " + e.getMessage());
                }
                
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final int statusCode = response.code();
                    String responseBody = response.body() != null ? response.body().string() : "empty body";
                    Log.d(TAG, "HTTP response received: status=" + statusCode + ", body=" + responseBody);
                    
                    if (statusCode >= 200 && statusCode < 300) {
                        Log.d(TAG, "Request successful with status: " + statusCode);
                        callback.onSuccess("Request successful with status: " + statusCode + ", response: " + responseBody);
                    } else {
                        Log.e(TAG, "Request failed with status: " + statusCode + ", response: " + responseBody);
                        callback.onError("Request failed with status: " + statusCode + ", response: " + responseBody);
                    }
                    response.close();
                }
            });
            
            Log.d(TAG, "Request enqueued");
            
        } catch (JSONException e) {
            Log.e(TAG, "JSON error", e);
            Log.e(TAG, "JSON error details: " + e.getMessage());
            callback.onError("Error creating request: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error in sendLocationUpdate", e);
            Log.e(TAG, "Error details: " + e.getMessage());
            callback.onError("Unexpected error: " + e.getMessage());
        }
    }
}
