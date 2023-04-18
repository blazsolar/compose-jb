import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            // Ignore only top and bottom edges. This mimics Android behaviour for when
            //  screen is oriented horizontaly. WindowInsets.systemBars contains information
            //  for .leadgin and .trailing edges, but compose by iteself doesn't apply those
            //  insets by default, similrat to what it does with top inset on AppBar.
            .ignoresSafeArea(.all, edges: [.top, .bottom])
    }
}



