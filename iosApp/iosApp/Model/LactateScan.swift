//
//  LactateScan.swift
//  iosApp
//
//  Created by KBS on 3/3/26.
//

import Foundation
import SwiftData

@Model
class LactateScan {

    var id: UUID
    var lactateValue: Double
    var sweatStatus: String
    var changeFromLast: Double
    var timestamp: Date

    init(
        id: UUID = UUID(),
        lactateValue: Double,
        sweatStatus: String,
        changeFromLast: Double,
        timestamp: Date = Date()
    ) {
        self.id = id
        self.lactateValue = lactateValue
        self.sweatStatus = sweatStatus
        self.changeFromLast = changeFromLast
        self.timestamp = timestamp
    }
}
