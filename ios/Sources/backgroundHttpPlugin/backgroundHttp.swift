import Foundation
import UIKit

@objc public class BackgroundHttp: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
    
    @objc public func sendLocationUpdate(url: String, token: String, username: String, latitude: Double, longitude: Double, timestamp: Double, completion: @escaping (Bool, String) -> Void) {
        // Create a background task
        var backgroundTaskID: UIBackgroundTaskIdentifier = .invalid
        backgroundTaskID = UIApplication.shared.beginBackgroundTask {
            UIApplication.shared.endBackgroundTask(backgroundTaskID)
            backgroundTaskID = .invalid
        }
        
        // Prepare the request
        guard let requestUrl = URL(string: url) else {
            completion(false, "Invalid URL")
            UIApplication.shared.endBackgroundTask(backgroundTaskID)
            return
        }
        
        var request = URLRequest(url: requestUrl)
        request.httpMethod = "POST"
        request.setValue("application/x-www-form-urlencoded", forHTTPHeaderField: "Content-Type")
        request.setValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        
        // Create features JSON
        let features = [
            [
                "attributes": [
                    "username": username,
                    "y": latitude,
                    "x": longitude,
                    "time": timestamp
                ]
            ]
        ]
        
        // Convert to JSON string
        let jsonData: Data
        do {
            jsonData = try JSONSerialization.data(withJSONObject: features)
        } catch {
            completion(false, "JSON serialization error: \(error.localizedDescription)")
            UIApplication.shared.endBackgroundTask(backgroundTaskID)
            return
        }
        
        let jsonString = String(data: jsonData, encoding: .utf8) ?? "[]"
        
        // Create form data
        let postString = "f=json&features=\(jsonString)"
        request.httpBody = postString.data(using: .utf8)
        
        // Execute request
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            defer {
                UIApplication.shared.endBackgroundTask(backgroundTaskID)
            }
            
            if let error = error {
                completion(false, "Request failed: \(error.localizedDescription)")
                return
            }
            
            guard let httpResponse = response as? HTTPURLResponse else {
                completion(false, "Invalid response")
                return
            }
            
            if httpResponse.statusCode >= 200 && httpResponse.statusCode < 300 {
                completion(true, "Request successful with status: \(httpResponse.statusCode)")
            } else {
                completion(false, "Request failed with status: \(httpResponse.statusCode)")
            }
        }
        task.resume()
    }
}
