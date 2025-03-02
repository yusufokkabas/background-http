import Foundation

@objc public class backgroundHttp: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
