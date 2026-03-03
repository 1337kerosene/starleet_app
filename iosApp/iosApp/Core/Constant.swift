//
//  Constant.swift.swift
//  Starleet
//
//  Created by KBS on 2/25/26.
//

//
import SwiftUI

extension Color{
    
    init(argb:UInt){
     let a = Double((argb >> 24) & 0xFF) / 255
        let r = Double((argb >> 16) & 0xFF) / 255
        let g = Double((argb >> 8) & 0xFF) / 255
        let b = Double(argb & 0xFF) / 255
        self.init(.sRGB,red: r,green: g,blue: b,opacity: a)
    }
}
extension Color {

    static let gradientTop = Color(hex: "#292929")
    static let gradientBottom = Color(hex: "#1E1E1E")
    static let welcome = Color(hex: "#2D2C31")
    static let tabBackColor = Color(hex: "#2B2B2B")
    static let tab_select = Color(hex: "#87E64C")
    static let tab_select_bg = Color(hex: "#475937")
    static let insight_bg = Color(hex: "#1b1b1b")
    static let good_color = Color(hex: "#00FFF2")
    static let balance_color = Color(hex: "#87E64C")
    static let impro_color = Color(hex: "#FF6804")
    static let yellow_color = Color(hex: "#F2E30C")
    static let low_color = Color(hex: "#14DC58")
    static let higher_color = Color(hex: "#FEB22B")
   
}
extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: .whitespacesAndNewlines)
            .replacingOccurrences(of: "#", with: "")

        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)

        let r, g, b: UInt64

        switch hex.count {
        case 6: // RGB
            (r, g, b) = (int >> 16, int >> 8 & 0xFF, int & 0xFF)
        default:
            (r, g, b) = (1, 1, 1)
        }

        self.init(
            .sRGB,
            red: Double(r) / 255,
            green: Double(g) / 255,
            blue: Double(b) / 255,
            opacity: 1
        )
    }
}

extension Font {

    static func manropeRegular(_ size: CGFloat) -> Font {
        .custom("Manrope-Regular", size: size)
    }

    static func manropeMedium(_ size: CGFloat) -> Font {
        .custom("Manrope-Medium", size: size)
    }

    static func manropeSemiBold(_ size: CGFloat) -> Font {
        .custom("Manrope-SemiBold", size: size)
    }

    static func manropeBold(_ size: CGFloat) -> Font {
        .custom("Manrope-Bold", size: size)
    }

    static func manropeLight(_ size: CGFloat) -> Font {
        .custom("Manrope-Light", size: size)
    }

    static func manropeExtraBold(_ size: CGFloat) -> Font {
        .custom("Manrope-ExtraBold", size: size)
    }

    static func manropeExtraLight(_ size: CGFloat) -> Font {
        .custom("Manrope-ExtraLight", size: size)
    }
}

extension Font {

    static let headingLargeManron = Font.manropeBold(26)
    static let headingManron = Font.manropeSemiBold(20)
    static let mediumManron = Font.manropeMedium(15)
    static let bodyManron = Font.manropeRegular(16)
    static let captionManron = Font.manropeLight(13)
    
    static let lightManron = Font.manropeLight(14)
    static let extraLightManron = Font.manropeExtraLight(17)

}


 func formatDate(_ date: Date) -> String {
    let formatter = DateFormatter()
    formatter.dateFormat = "dd MMM"
    return formatter.string(from: date)
     
     
}

func formatDateKey(_ ts: Int64) -> String {
    let date = Date(timeIntervalSince1970: Double(ts) / 1000)
    let formatter = DateFormatter()
    formatter.dateFormat = "dd-MM-yyyy"
    return formatter.string(from: date)
}
func formatHourKey(_ ts: Int64) -> String {
    let date = Date(timeIntervalSince1970: Double(ts) / 1000)
    let formatter = DateFormatter()
    formatter.dateFormat = "HH:00"
    return formatter.string(from: date)
}

func getHourSlot(_ ts: Int64) -> Int {
    let date = Date(timeIntervalSince1970: Double(ts) / 1000)
    let formatter = DateFormatter()
    formatter.dateFormat = "HH:mm"
    return Int(formatter.string(from: date)) ?? 0
}

func todayKey() -> String {
    let formatter = DateFormatter()
    formatter.dateFormat = "dd-MM-yyyy"
    return formatter.string(from: Date())
}

func formatHourToAMPM(_ hourString: String) -> String {

    let formatter = DateFormatter()
    formatter.dateFormat = "HH"

    if let date = formatter.date(from: hourString) {
        formatter.dateFormat = "h a"
        return formatter.string(from: date)
    }

    return hourString
}

func formatMinuteTime(_ ts: Int64) -> String {
    let date = Date(timeIntervalSince1970: TimeInterval(ts / 1000))
    let formatter = DateFormatter()
    formatter.dateFormat = "HH:mm"
    return formatter.string(from: date)
}

extension String {
    func isValidEmail() -> Bool {
        let pattern = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        let regex = try? NSRegularExpression(pattern: pattern)
        let range = NSRange(location: 0, length: self.utf16.count)
        return regex?.firstMatch(in: self, options: [], range: range) != nil
    }

    func isValidPassword() -> Bool {
        let pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{1,10}$"
        let regex = try? NSRegularExpression(pattern: pattern)
        let range = NSRange(location: 0, length: self.utf16.count)
        return regex?.firstMatch(in: self, options: [], range: range) != nil
    }
    static let appName = "ViaClara"

}


let last7Days: [String] = {
    let formatter = DateFormatter()
    formatter.dateFormat = "dd MMM yyyy"
    
    return (0..<7).map {
        Calendar.current.date(byAdding: .day, value: -$0, to: Date())!
    }.map {
        formatter.string(from: $0)
    }
}()


 let timeFormatter: DateFormatter = {
    let formatter = DateFormatter()
    formatter.dateFormat = "hh:mm a"
    return formatter
}()
