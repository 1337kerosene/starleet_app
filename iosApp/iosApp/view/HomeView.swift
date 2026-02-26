//
//  Overview.swift
//  Starleet
//
//  Created by Apple on 24/02/26.
//
import SwiftUI
struct HomeView : View {
    var body: some View {
        ScrollView(showsIndicators: false){
            VStack{
                VStack(alignment: .leading ){
                    Text("Overview")
                        .font(.headingManron)
                        .foregroundStyle(Color.white)
                }
                .padding()
                .frame(maxWidth: .infinity,maxHeight: .infinity,alignment: .topLeading)
                VStack(alignment: .leading, spacing:10){
                    CurrentLactate()
                    TrendChartView()
                    HistoryView()
                }
           }
            
        }
        .background(Color.bg)
    }
}
struct CurrentLactate: View {
      let mmvalue: Double = 0.3
     var formattedValue: String { String(format: "%.1f", mmvalue) }
      var sweatvaluef: String { String(format: "%.1f",sweatvalue) }
      let sweatvalue: Double = 0.1
    var body: some View {
        ZStack{
            HStack {
                VStack(alignment: .leading, spacing: 8){
                    Text("Current Lactate")
                        .font(.mediumManron)
                        .foregroundStyle(.greenBg)
                    HStack(alignment: .bottom, spacing: 6) {
                        Text("\(sweatvaluef) mM")
                            .font(.headingManron)
                            .font(.title3)
                            .foregroundStyle(Color.white)
                        Text("No Sweat")
                            .foregroundStyle(Color(argb: 0xffD0D0D0))
                            .font(.captionManron)
                    }
                    Text("↓ \(formattedValue) mM vs Last scan")
                        .font(.captionManron)
                        .foregroundStyle(Color(argb: 0xffD0D0D0))
                }
                Spacer()
            }
            .padding()
            .frame(maxWidth: .infinity)
            .background(
                ZStack {
                    RoundedRectangle(cornerRadius: 15)
                        .fill(Color.cardBg)
                    RoundedRectangle(cornerRadius: 15)
                        .fill(
                            LinearGradient(
                                stops: [
                                    .init(color: Color.greenBg, location: 0),
                                    .init(color: Color.cardBg3, location: 1),
                                    .init(color: Color.black.opacity(0.20), location: 0.001)
                                ],
                                startPoint: .trailing,
                                endPoint: .leading
                            ).opacity(0.09)
                        )
                }
            )
        }
        .padding(.horizontal,10)
        }
}
struct HistoryView: View {
let data = [
    ("07:03", "0.0 mM"),
    ("07:32", "2.8 mM"),
    ("07:40", "2.9 mM"),
    ("07:48", "2.5 mM")
]
    @State private var isExpanded = false
    @State private var selectedDate = "Select Date"
    var body: some View {
        DateDropdownView()
                   VStack(spacing: 0) {
                HStack {
                    Text("Time")
                        .foregroundColor(.white.opacity(0.7))
                    Spacer()
                    Text("Lactate")
                        .foregroundColor(.white.opacity(0.7))
                }
                .padding()
                Divider()
                    .background(Color.white.opacity(0.2))
                       ForEach(data.indices, id: \.self) { index in
                           let item = data[index]
                           HStack {
                               Text(item.0)
                                   .foregroundColor(.white)
                               Spacer()
                               HStack(spacing: 15) {
                                   Text(item.1)
                                       .foregroundColor(.white)
                                   Image(systemName: "chevron.right")
                                       .foregroundStyle(Color.white)
                               }
                           }
                           .padding()

                           if index != data.count - 1 {
                               Divider()
                                   .background(Color.white.opacity(0.1))
                           }
                    }
                   }
        .padding()
        .background(
            ZStack {
                RoundedRectangle(cornerRadius: 15)
                    .fill(Color.cardBg)
                RoundedRectangle(cornerRadius: 15)
                    .fill(
                        LinearGradient(
                            stops: [
                                .init(color: Color.greenBg, location: 0),
                                .init(color: Color.cardBg3, location: 1),
                                .init(color: Color.black.opacity(0.20), location: 0.001)
                            ],
                            startPoint: .trailing,
                            endPoint: .leading
                        ).opacity(0.09)
                    )
            }
                .overlay(
                    RoundedRectangle(cornerRadius: 15)
                        .stroke(Color.white.opacity(0.2), lineWidth: 1)
                )
        )
        .padding(.horizontal,10)

    }
}

struct DateDropdownView: View {

    @State private var selectedDate = "Select Date"

    let last7Days: [String] = {
        let formatter = DateFormatter()
        formatter.dateFormat = "dd MMM yyyy"
        return (0..<7).map {
            Calendar.current.date(byAdding: .day, value: -$0, to: Date())!
        }.map {
            formatter.string(from: $0)
        }
    }()

    var body: some View {
        VStack(alignment: .trailing, spacing: 0) {

            HStack {
                Text("History")
                    .fontWeight(.semibold)
                    .foregroundColor(.white)

                Spacer()

                Picker("", selection: $selectedDate) {
                    Text("Select Date").tag("Select Date")

                    ForEach(last7Days, id: \.self) { date in
                        Text(date).tag(date)
                    }
                }
                .pickerStyle(.menu)
                .tint(.white)
                .padding(.horizontal, 12)
                .frame(height: 36)
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(Color.white.opacity(0.5), lineWidth: 1)
                )
            }
        }
        .padding(15)
    }
}
