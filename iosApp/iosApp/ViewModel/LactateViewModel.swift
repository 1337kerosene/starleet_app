//
//  LactateViewModel.swift
//  iosApp
//
//  Created by KBS on 3/3/26.
//

import SwiftUI
import SwiftData
import AVFoundation

@MainActor
class LactateViewModel: ObservableObject {

    @Published private(set) var state = LactateState()

    private let repository: LactateRepository

    init(repository: LactateRepository) {
        self.repository = repository
        processIntent(.loadLactate)
    }

    func processIntent(_ intent: LactateIntent) {
        switch intent {

        case .loadLactate:
            loadLactate()

        case .addScan(let value, let sweat, let change):
            addScan(value: value, sweat: sweat, change: change)

        case .selectHistoryDate(let date):
            state.selectedDate = date
            loadLactate()
        }
    }

    private func loadLactate() {

        let allData = repository.getAllScans()

        if let selectedDate = state.selectedDate {

            let start = Calendar.current.startOfDay(for: selectedDate)
            let end = Calendar.current.date(byAdding: .day, value: 1, to: start)!

            state.history = allData.filter {
                $0.timestamp >= start && $0.timestamp < end
            }

        } else {
            state.history = allData
        }

        state.trend = Array(allData.prefix(7))
        state.current = repository.getLatestScan()
    }

    private func addScan(value: Double, sweat: String, change: Double) {

        let randomValue = (value + Double.random(in: 0...1))
            .clamped(to: 0...4)
            .rounded(toPlaces: 1)

        let randomChange = Double.random(in: -0.5...0.5)
            .rounded(toPlaces: 1)

        repository.insertScan(
            value: randomValue,
            sweat: sweat,
            change: randomChange
        )

        loadLactate()
    }
}
extension Double {
    func rounded(toPlaces places: Int) -> Double {
        let divisor = pow(10.0, Double(places))
        return (self * divisor).rounded() / divisor
    }

    func clamped(to range: ClosedRange<Double>) -> Double {
        min(max(self, range.lowerBound), range.upperBound)
    }
}
