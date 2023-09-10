//
//  ComposeView.swift
//  iosApp
//
//  Created by Öner, Hamza on 10.09.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
    }
    
    func makeUIViewController(context: Context) -> some UIViewController {
        Main_iosKt.MainViewController()
    }
}
