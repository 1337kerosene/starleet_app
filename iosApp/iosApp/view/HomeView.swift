//
//  Overview.swift
//  Starleet
//
//  Created by Apple on 24/02/26.
//
import SwiftUI
import SwiftData

struct HomeView : View {
    
    @StateObject private var viewModel: LactateViewModel
    init(context: ModelContext) {
        _viewModel = StateObject(
            wrappedValue: LactateViewModel(
                repository: LactateRepository(context: context)
            )
        )
    }

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
                    CurrentLactate(viewModel: viewModel)
                    TrendChartView(viewModel: viewModel)
                    HistoryView(viewModel: viewModel)
                }
           }
            
        }
        .background(Color.bg)
    }
}
struct CurrentLactate: View {
    @StateObject var viewModel: LactateViewModel
      var body: some View {
        ZStack{
            HStack {
                VStack(alignment: .leading, spacing: 8){
                    Text("Current Lactate")
                        .font(.mediumManron)
                        .foregroundStyle(.greenBg)
                    HStack(alignment: .bottom, spacing: 6) {
                        Text(String(format: "%.1f mM", (viewModel.state.history.last?.lactateValue ?? 0.0)))
                            .font(.headingManron)
                            .font(.title3)
                            .foregroundStyle(Color.white)
                        Text("\(viewModel.state.history.last?.sweatStatus ?? "")")
                            .foregroundStyle(Color(argb: 0xffD0D0D0))
                            .font(.captionManron)
                    }
                    Text("↓ \(String(format: "%.1f mM", (viewModel.state.history.last?.changeFromLast ?? 0.0))) vs Last scan")
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
   
    @State private var isExpanded = false
    @State private var selectedDate = "Select Date"
    @StateObject var viewModel: LactateViewModel

    var body: some View {
        DateDropdownView(viewModel: viewModel)
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
                       ForEach(viewModel.state.history) { item in
                           HStack {
                               Text("\(timeFormatter.string(from: item.timestamp))")
                                   .foregroundColor(.white)

                               Spacer()
                               HStack(spacing: 15) {
                                   Text(String(format: "%.1f mM", item.lactateValue))
                                       .foregroundColor(.white)
                                   Image(systemName: "chevron.right")
                                       .foregroundStyle(Color.white)
                               }
                           }
                           .padding()

                           if item.id != viewModel.state.history.last?.id {
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

    @ObservedObject var viewModel: LactateViewModel

    @State private var selectedDate: Date? = nil
    @State private var showCalendar = false

    // Last 7 days range
//    private var minDate: Date {
//        Calendar.current.date(byAdding: .day, value: -6, to: Date())!
//    }
//    private var maxDate: Date {
//        Date()
//    }
    var body: some View {
        VStack(alignment: .trailing, spacing: 0) {

            HStack {
                Text("History")
                    .fontWeight(.semibold)
                    .foregroundColor(.white)

                Spacer()

                Button {
                    showCalendar = true
                } label: {
                    Text(
                        selectedDate != nil
                        ? selectedDate!.formatted(date: .abbreviated, time: .omitted)
                        : "Select Date"
                    )
                    .foregroundColor(.white)
                    .padding(.horizontal, 12)
                    .frame(height: 36)
                }
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(Color.white.opacity(0.5), lineWidth: 1)
                )
            }
        }
        .padding(15)

        // MARK: - Calendar Sheet
        .sheet(isPresented: $showCalendar) {
            VStack {

                DatePicker(
                    "Select Date",
                    selection: Binding(
                        get: { selectedDate ?? Date() },
                        set: { newDate in
                            selectedDate = newDate
                            viewModel.processIntent(.selectHistoryDate(newDate))
                        }
                    ),
//                    in: minDate...maxDate,
                    displayedComponents: .date
                )
                .datePickerStyle(.graphical)
                .padding()

                Button("Done") {
                    showCalendar = false
                }
                .padding()
            }
        }
    }
}
