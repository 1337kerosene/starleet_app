//  WifiScanView.swift
//  Starleet
//
//  Created by KBS on 2/25/26.
//
import SwiftUI
import SwiftData

 struct WifiScanView: View {
 @State private var mMvalue = 0.1
 @State private var title = "Lactate"
     
var formattedValue: String {
    String(format: "%.1f", mMvalue)
}
    var body: some View {
                  VStack {
                HStack {
                    Text("Scan Lactate")
                        .font(.headingManron)
                        .foregroundColor(.white)
                    Spacer()
                }
                .padding(.horizontal)
                .padding(.top, 20)
                VStack(spacing: 6) {
                    Text("\(formattedValue) mM")
                        .font(.headingLargeManron)
                        .foregroundColor(Color(argb: 0xFF6DDBD3))
                    Text("Lactate ( No Sweat )")
                        .font(.bodyManron)
                        .foregroundColor(Color(argb: 0xffB5B5B5))
                }
                .padding(.top, 40)
                PulseButton()
            }
}
}

struct PulseButton: View {
    @State private var animate = false
    
    @Environment(\.modelContext) private var context
    @StateObject private var viewModel: LactateViewModel
    
    @Query(sort: \LactateScan.timestamp, order: .reverse)
    private var scans: [LactateScan]
    
    init(context: ModelContext? = nil) {
        // Temporary dummy init (real one happens in body)
        _viewModel = StateObject(
            wrappedValue: LactateViewModel(
                repository: LactateRepository(
                    context: ModelContext(
                        try! ModelContainer(for: LactateScan.self)
                    )
                )
            )
        )
    }

    
    
    var body: some View {
        let repo = LactateRepository(context: context)

        VStack {
            Button(action: {
                print("Tapped")
            }) {
                ZStack {
                    ForEach(0..<3) { i in
                        Circle()
                            .fill(Color.greenBg)
                            .scaleEffect(animate ? 1.8 : 0.8)
                            .opacity(animate ? 0 : 1)
                            .animation(
                                animate ?
                                .easeOut(duration: 3.5)
                                .repeatForever()
                                .delay(Double(i) * 1.0)
                                : .default,
                                value: animate
                            )
                    }

                    Circle()
                        .fill(Color.greenBg)
                        .frame(width: 80, height: 80)
                }
            }
            .frame(width: 200, height: 200)
            .padding(.top,60)
            
            HStack{
                
                Button {
                    if animate {
                        animate = false
                        viewModel.processIntent(
                                            .addScan(value: 2.9,
                                                     sweat: "No Sweat",
                                                     change: 0.3)
                                        )
                    } else {
                        animate = true
                       
                    }
                } label: {
                    Text(animate ? "Stop Scan" : "Start Scan")
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .frame(height: 50)
                        .background(Color(argb: 0xff24A19D))
                        .cornerRadius(10)
                        .padding(.horizontal)
                }
            }
            .padding()
            .frame(maxWidth: .infinity,maxHeight: .infinity,alignment: .bottom)
        }
        .onAppear {
                   viewModel.processIntent(.loadLactate)
            }
    }
}
