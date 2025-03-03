import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(BackgroundHttpPlugin)
public class BackgroundHttpPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "BackgroundHttpPlugin"
    public let jsName = "BackgroundHttp"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "sendLocationUpdate", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = BackgroundHttp()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    @objc func sendLocationUpdate(_ call: CAPPluginCall) {
        guard let url = call.getString("url"),
              let token = call.getString("token"),
              let username = call.getString("username"),
              let latitude = call.getDouble("latitude"),
              let longitude = call.getDouble("longitude") else {
            call.reject("Missing required parameters")
            return
        }
        
        // Get timestamp as integer (seconds since epoch)
        guard let timestampInt = call.getInt("timestamp") else {
            call.reject("Missing timestamp parameter")
            return
        }
        
        // Convert to double for the implementation
        let timestamp = Double(timestampInt)
        
        print("Raw timestamp value from call: \(call.options["timestamp"] ?? "nil")")
        print("Timestamp retrieved as Integer: \(timestampInt)")
        print("Timestamp converted to Double: \(timestamp)")
        
        implementation.sendLocationUpdate(
            url: url,
            token: token,
            username: username,
            latitude: latitude,
            longitude: longitude,
            timestamp: timestamp
        ) { success, message in
            if success {
                call.resolve([
                    "success": success,
                    "message": message
                ])
            } else {
                call.reject(message)
            }
        }
    }
}
