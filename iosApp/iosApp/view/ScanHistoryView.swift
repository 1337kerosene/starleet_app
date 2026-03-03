import SwiftUI
import SwiftData

struct ScanHistoryView : View {
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
            VStack(alignment: .leading ){
                VStack(alignment: .leading ){
                    Text("Scan History")
                        .fontWeight(.semibold)
                        .foregroundStyle(Color.white)
                }
                .padding()
                .frame(maxWidth: .infinity,maxHeight: .infinity,alignment: .topLeading)
                CalenderView(viewModel:viewModel)
                ScansRecordedView(viewModel: viewModel)
            }
        }
    }
}
struct CalenderView: View {
    @State private var currentMonth = Date()
    @State private var selectedDate: Date? = nil

    private let calendar = Calendar.current
    @StateObject var viewModel: LactateViewModel

        var body: some View {
        VStack(spacing: 16) {
            HStack {
                Button {
                    changeMonth(by: -1)
                } label: {
                    Image("left_arr")
                        .foregroundColor(.white)
                }
                .padding()
                .frame(maxWidth: 40)
                .frame(maxHeight: 40)
                .background(
                    RoundedRectangle(cornerRadius: 10)
                        .fill(Color(argb: 0xff2B2B2B))
                )
                Spacer()
                     Text(monthYearString(from: currentMonth))
                    .foregroundColor(.white)
                    .font(.headline)
                
                Spacer()
                
                Button {
                    changeMonth(by: 1)
                } label: {
                    Image("right_arr")
                        .foregroundColor(.white)
                }
                .padding()
                .frame(maxWidth: 40)
                .frame(maxHeight: 40)
                .background(
                    RoundedRectangle(cornerRadius: 10)
                        .fill(Color(argb: 0xff2B2B2B))
                )
         
            }
            HStack {
                ForEach(calendar.shortWeekdaySymbols, id: \.self) { day in
                    Text(day.prefix(3))
                        .foregroundColor(.gray)
                        .frame(maxWidth: .infinity)
                        .font(.caption)
                }
            }
            let days = generateDays()
            LazyVGrid(columns: Array(repeating: GridItem(.flexible()), count: 7)) {
                ForEach(days, id: \.self) { date in
                    if let date = date {
                        dayView(for: date)
                    } else {
                        Text("")
                            .frame(height: 40)
                    }
                }
            }
        }
        .frame(maxWidth: .infinity,maxHeight: .infinity,alignment: .top)
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
        )
        .cornerRadius(20)
        .padding()
    }
}

   extension CalenderView {
        private func monthYearString(from date: Date) -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMM yyyy"
        return formatter.string(from: date)
    }
        private func changeMonth(by value: Int) {
        if let newMonth = calendar.date(byAdding: .month, value: value, to: currentMonth) {
            currentMonth = newMonth
        }
    }
    private func generateDays() -> [Date?] {
        guard let monthInterval = calendar.dateInterval(of: .month, for: currentMonth),
              let firstWeekday = calendar.dateComponents([.weekday], from: monthInterval.start).weekday else {
            return []
        }
        var days: [Date?] = Array(repeating: nil, count: firstWeekday - 1)
                var currentDate = monthInterval.start
        while currentDate < monthInterval.end {
            days.append(currentDate)
            currentDate = calendar.date(byAdding: .day, value: 1, to: currentDate)!
        }
        return days
    }
        private func dayView(for date: Date) -> some View {
            let isSelected = calendar.isDate(date, inSameDayAs: selectedDate ?? Date())
        return Text("\(calendar.component(.day, from: date))")
            .foregroundColor(isSelected ? .white : .gray)
            .frame(maxWidth: .infinity, minHeight: 40)
            .background(
                ZStack {
                    if isSelected {
                                RoundedRectangle(cornerRadius: 10)
                                .fill(Color(argb: 0xff24A19D))
                                .frame(width: 48, height: 28)
                    }
                }
            )
            .onTapGesture {
                selectedDate = date
                viewModel.processIntent(.selectHistoryDate(date))
            }
    }
}
struct ScansRecordedView:View {

    @StateObject var viewModel: LactateViewModel
    var body: some View {
        let selectedDate = viewModel.state.selectedDate ?? Date()
        VStack(alignment: .leading,spacing:20){
            VStack(spacing: 5) {
                Text(selectedDate.formatted(
                    .dateTime.weekday(.wide)
                    .day()
                    .month(.abbreviated)
                ))
                    .foregroundStyle(.white)
                    .font(.headingManron)
                    .frame(maxWidth: .infinity,alignment: .leading)
                Text("\(viewModel.state.history.count) Scans Recorded")
                    .foregroundStyle(.gray)
                    .font(.mediumManron)
                    .frame(maxWidth: .infinity,alignment: .leading)
            }
            .padding(.horizontal,20)
 
            VStack(spacing: 16) {
                ForEach(viewModel.state.history) { item in
                    HStack(spacing: 12) {
                        RoundedRectangle(cornerRadius: 3)
                            .fill(Color(
                                red: Double.random(in: 0.3...1),
                                green: Double.random(in: 0.3...1),
                                blue: Double.random(in: 0.3...1)
                            ))
                         .frame(width: 4, height: 50)
                         VStack(alignment: .leading, spacing: 6) {
                            Text("\(timeFormatter.string(from: item.timestamp))")
                                .foregroundColor(.white.opacity(0.6))
                                .font(.system(size: 14))
                            Text("Lactate: \(String(format: "%.1f mM", item.lactateValue))")
                                .foregroundColor(.white.opacity(0.6))
                                .font(.system(size: 18, weight: .medium))
                        }
                        Spacer()
                    }
                    .padding(.vertical, 10)
                }
            }
            .padding()
        }
    }
}
