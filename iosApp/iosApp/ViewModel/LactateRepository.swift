//
//  LactateRepository.swift
//  iosApp
//
//  Created by KBS on 3/3/26.
//

import SwiftData
import AVFoundation

class LactateRepository {

    private let context: ModelContext

    init(context: ModelContext) {
        self.context = context
    }

    // Insert
    func insertScan(value: Double, sweat: String, change: Double) {
        let scan = LactateScan(
            lactateValue: value,
            sweatStatus: sweat,
            changeFromLast: change
        )

        context.insert(scan)
        try? context.save()
    }

    // Get All
    func getAllScans() -> [LactateScan] {
        let descriptor = FetchDescriptor<LactateScan>(
            sortBy: [SortDescriptor(\.timestamp, order: .reverse)]
        )
        return (try? context.fetch(descriptor)) ?? []
    }

    // Last 7
    func getLast7Scans() -> [LactateScan] {
        var descriptor = FetchDescriptor<LactateScan>(
            sortBy: [SortDescriptor(\.timestamp, order: .reverse)]
        )
        descriptor.fetchLimit = 7
        return (try? context.fetch(descriptor)) ?? []
    }

    // Latest
    func getLatestScan() -> LactateScan? {
        var descriptor = FetchDescriptor<LactateScan>(
            sortBy: [SortDescriptor(\.timestamp, order: .reverse)]
        )
        descriptor.fetchLimit = 1
        return try? context.fetch(descriptor).first
    }
}
