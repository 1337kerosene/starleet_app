import SwiftUI
import Charts
    struct TrendData: Identifiable {
        let id = UUID()
        let date: Date
        let value: Double
    }
struct TrendChartView: View {
    
    @StateObject var viewModel: LactateViewModel
    
    var trendChartData: [TrendData] {
        var calendar = Calendar.current
        calendar.firstWeekday = 2 // Monday

        let today = Date()

        // Get start of week (Monday)
        let weekInterval = calendar.dateInterval(of: .weekOfYear, for: today)!
        let startOfWeek = weekInterval.start

        // Generate Monday → Sunday
        let weekDays = (0..<7).compactMap {
            calendar.date(byAdding: .day, value: $0, to: startOfWeek)
        }

        // Group scans by day
        let grouped = Dictionary(grouping: viewModel.state.trend) {
            calendar.startOfDay(for: $0.timestamp)
        }

        return weekDays.map { day in
            let scans = grouped[calendar.startOfDay(for: day)] ?? []
            let value = scans.last?.lactateValue ?? 0

            return TrendData(date: day, value: value)
        }
    }
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack{
                Text("Trend")
                    .font(.headline)
                    .foregroundColor(.white)
                Text("(Last 7 scans)")
                    .font(.caption)
                    .foregroundColor(.white.opacity(0.6))
            }
            Chart {
                ForEach(trendChartData) { item in
                    AreaMark(
                        x: .value("Date", item.date),
                        yStart: .value("Start", 0),
                        yEnd: .value("Value", item.value)
                    )
                    .interpolationMethod(.catmullRom)
                    .foregroundStyle(
                        LinearGradient(
                            colors: [
                                Color(argb: 0xff24A19D),
                                Color(argb: 0xff333333)
                            ],
                            startPoint: .center,
                            
                            endPoint:.bottomLeading
                        )
                    )
                }
            }
            .chartXScale(range: .plotDimension(padding: 0))
            .chartYScale(domain: 0...4)
            .chartXAxis {
                AxisMarks(values: trendChartData.map { $0.date }) { value in
                    AxisGridLine(stroke: StrokeStyle(lineWidth: 1, dash: [5]))
                        .foregroundStyle(.white.opacity(0.2))

                    AxisValueLabel(format: .dateTime.day().month(.abbreviated))
                        .foregroundStyle(.white)
                }
            }
            .frame(height: 220)
       
            .chartYAxis {
                AxisMarks(position: .leading, values: [0, 2, 4]) { value in
                    AxisGridLine(stroke: StrokeStyle(lineWidth: 1, dash: [5]))
                        .foregroundStyle(Color.white.opacity(0.2))
                    
                    AxisValueLabel {
                        if let val = value.as(Double.self) {
                            Text("\(Int(val))mM")
                                .foregroundStyle(Color(argb: 0xffD0D0D0))
                        }
                    }
                }
            }
            .frame(height: 220)
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
        )
        .cornerRadius(16)
        .padding(.horizontal,10)
    }
}
