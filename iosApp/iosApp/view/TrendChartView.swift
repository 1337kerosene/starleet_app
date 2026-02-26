import SwiftUI
import Charts

struct TrendChartView: View {
    struct TrendData: Identifiable {
        let id = UUID()
        let date: Date
        let value: Double
    }
    let data: [TrendData] = {
        let calendar = Calendar.current
        return [
            .init(date: calendar.date(from: DateComponents(year: 2026, month: 1, day: 9))!, value: 0.8),
            .init(date: calendar.date(from: DateComponents(year: 2026, month: 1, day: 11))!, value: 1.5),
            .init(date: calendar.date(from: DateComponents(year: 2026, month: 1, day: 13))!, value: 1.8),
            .init(date: calendar.date(from: DateComponents(year: 2026, month: 1, day: 15))!, value: 1.2),
            .init(date: calendar.date(from: DateComponents(year: 2026, month: 1, day: 17))!, value: 1.0),
            .init(date: calendar.date(from: DateComponents(year: 2026, month: 1, day: 19))!, value: 2.8),
            .init(date: calendar.date(from: DateComponents(year: 2026, month: 1, day: 21))!, value: 3.2),
            .init(date: calendar.date(from: DateComponents(year: 2026, month: 1, day: 23))!, value: 3.6)
        ]
    }()
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
                ForEach(data) { item in
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
                AxisMarks(values: .stride(by: .day, count: 4)) { value in
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
