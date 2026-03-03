import SwiftUI

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            NavigationStack {
                Tabview()
            }
        }
        .modelContainer(for: LactateScan.self)

    }
}
