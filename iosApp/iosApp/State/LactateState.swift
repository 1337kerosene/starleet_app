//
//  LactateState.swift
//  iosApp
//
//  Created by KBS on 3/3/26.
//
import AVFoundation

struct LactateState {
    var current: LactateScan?
    var history: [LactateScan] = []
    var trend: [LactateScan] = []
    var isLoading: Bool = false
    var error: String?
    var selectedDate: Date?
}
