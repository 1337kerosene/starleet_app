//
//  LactoseEntry.swift
//  Starleet
//
//  Created by KBS on 2/25/26.
//
import AVFoundation
import SwiftUI

struct LactoseEntry: Identifiable {
    let id = UUID()
    let time: String
    let value: Double
    let color: Color
}
