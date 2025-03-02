// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "BackgroundHttp",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "BackgroundHttp",
            targets: ["backgroundHttpPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "backgroundHttpPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/backgroundHttpPlugin"),
        .testTarget(
            name: "backgroundHttpPluginTests",
            dependencies: ["backgroundHttpPlugin"],
            path: "ios/Tests/backgroundHttpPluginTests")
    ]
)