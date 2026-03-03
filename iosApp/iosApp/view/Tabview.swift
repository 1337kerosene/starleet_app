//
//  Tabview.swift
//  Starleet
//
//  Created by Apple on 24/02/26.
//
import SwiftUI
struct Tabview: View {
    @State private var selectedTab = 0
    @Environment(\.modelContext) private var context

    var body: some View {
        ZStack (alignment: .bottom){
            Color.bg
                .ignoresSafeArea()
            VStack(spacing: 0) {
                ZStack {
                    switch selectedTab {
                    case 0:
                        HomeView(context: context)
                    case 1:
                        ScanHistoryView(context: context)
                        
                    case 2:
                        WifiScanView()
                        
                    case 3:
                        SensorCalibrationView()
                        
//                    case 4:
//                        SettingView()

                  
                    default:
                        EmptyView()
                    }
                }
                TabBarViewModel(selectedTab: $selectedTab)
            }
        }
    }
}
struct TabBarViewModel: View {
    @Binding var selectedTab: Int
    var body: some View {
        ZStack{
                     RoundedRectangle(cornerRadius: 15)
                .fill(
                    LinearGradient(
                        colors: [
                            Color(argb: 0xff333333),
                            Color(argb: 0xff333333)
                        ],
                        startPoint: .leading,
                        endPoint: .trailing
                    )
                )
            HStack (spacing: 20) {
                TabBarModel(
                    imageName: "home",
                    isSelected: selectedTab == 0
                ) { selectedTab = 0 }
              
                TabBarModel(
                    imageName: "calender",
                    isSelected: selectedTab == 1
                ) { selectedTab = 1 }
                        TabBarModel(
                    imageName: "scan",
                    isSelected: selectedTab == 2
                ) { selectedTab = 2 }
                TabBarModel(
                    imageName: "setting",
                    isSelected: selectedTab == 3
                ) { selectedTab = 3 }
            }

        }
        .frame(maxHeight: 80)
    }
}
struct TabBarModel: View {
    let imageName: String
    let isSelected: Bool
    let action: () -> Void
    var body: some View {
        HStack(spacing: 8) {
            Image(imageName)
                .resizable()
                .renderingMode(.template)
                .frame(width: 25, height: 25)
                .foregroundColor(isSelected ? Color(argb: 0xff6DDBD3) :Color(argb: 0xffAFAFAF)  )
        }
        .padding(.horizontal, 16)
        .onTapGesture {
            action()
        }
    }
}
