//
//  LactateIntent.swift
//  iosApp
//
//  Created by KBS on 3/3/26.
//

import Foundation

enum LactateIntent {
    case addScan(value: Double, sweat: String, change: Double)
    case selectHistoryDate(Date)
    case loadLactate
}
