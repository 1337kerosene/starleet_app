//
//  SensorCalibrationView.swift
//  iosApp
//
//  Created by KBS on 2/27/26.
//

import SwiftUI

struct SensorCalibrationView: View {
    @State private var listARR = ["Bias","Calibration"]
    @State private var isBias = false
    @State private var isCalibration = false

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text("Settings")
                .font(.headingManron)
                .foregroundStyle(Color.white)
                .padding(.horizontal)
                .padding(.top, 10)
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity, alignment: .topLeading)
                VStack(spacing:20) {
                    ForEach(listARR.indices, id: \.self) { index in
                        Button(action: {
                            if listARR[index] == "Bias" {
                                isBias = true
                                isCalibration = false
                            } else {
                                isCalibration = true
                                isBias = false
                            }
                        }) {
                            CalibrationRow(title: listARR[index])
                        }
                    }
                    .navigationDestination(isPresented: $isBias) {
                        BiasView()
                    }
                    .navigationDestination(isPresented: $isCalibration) {
                        CalibrationView()
                    }
                } .padding(.vertical,80)
                    .frame(maxWidth:.infinity, maxHeight: .infinity, alignment: .top)
            }
        
}

#Preview {
    SensorCalibrationView()
}
//MARK:Calibratio List
struct CalibrationRow: View {
    var title: String
    var body: some View {
        HStack {
            Text(title)
                .foregroundColor(.white)
                .font(.system(size: 16, weight: .medium))
            Spacer()
            Image(systemName: "chevron.right")
                .foregroundColor(.white.opacity(0.5))
        }
        .padding()
        .frame(height: 55)
        .background(
            ZStack {
                RoundedRectangle(cornerRadius: 15)
                    .fill(Color.cardBg)
                RoundedRectangle(cornerRadius: 15)
                    .fill(
                        LinearGradient(
                            colors: [
                                Color.greenBg,
                                Color.cardBg3,
                                Color.black.opacity(0.2)
                            ],
                            startPoint: .trailing,
                            endPoint: .leading
                        )
                        .opacity(0.09)
                    )
            }
        )
        .padding(.horizontal, 15)
        .contentShape(Rectangle())
    }
}
//MARK:BiasView
struct BiasView: View {
 @State private var biasValue: Double = 0
 var body: some View {
     ZStack {
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
             .ignoresSafeArea()
         VStack{
             VStack(spacing: 20) {
                 HStack{
                     Spacer()
                     Button {
                         biasValue  = 0
                     } label: {
                         Image("ic_refresh")
                     }
                 }
                 HStack {
                     Text("Bias (mV)")
                         .font(.headingManron)
                         .foregroundColor(.white)
                 }
                 Text("\(Int(biasValue)) mV")
                     .font(.headingManron)
                     .foregroundStyle(Color(argb: 0xff6DDBD3))
                 Slider(value: $biasValue, in: -150...150, step: 1)
                     .tint(Color(argb: 0xff24A19D))
                 HStack {
                     Text("-150 mV")
                     Spacer()
                     Text("150 mV")
                 }
                 .foregroundStyle(Color.white)
                 Text("Changing this value may affect measurement accuracy.")
                     .font(.captionManron)
                     .lineLimit(1)
                     .foregroundColor(.white.opacity(0.6))
                     .multilineTextAlignment(.center)
             }
             .padding(15)
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
             .padding()
         }
         .frame(maxWidth: .infinity,maxHeight: .infinity,alignment: .top)
         SensorCalibrationButtons()
     }
 }
}
//MARK:CalibrationView
struct CalibrationView: View {
 @State private var CalibrationValue: Double = 0
 var body: some View {
     ZStack {
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
             .ignoresSafeArea()
         VStack{
             VStack(spacing: 20) {
                 HStack{
                     Spacer()
                     Button {
                         CalibrationValue = 0
                     } label: {
                         Image("ic_refresh")
                     }
                 }
                 HStack {
                     Text("Calibration")
                         .font(.headingManron)
                         .foregroundColor(.white)
                 }
                 Text("\(Int(CalibrationValue)) %")
                     .font(.headingManron)
                     .foregroundStyle(Color(argb: 0xff6DDBD3))
                 Slider(value: $CalibrationValue, in: -0...100, step: 1)
                     .tint(Color(argb: 0xff24A19D))
                 HStack {
                     Text("0 %")
                     Spacer()
                     Text("100 %")
                 }
                 .foregroundStyle(Color.white)
                 Text("Changing this value may affect measurement accuracy.")
                     .font(.captionManron)
                     .lineLimit(1)
                     .foregroundColor(.white.opacity(0.6))
                     .multilineTextAlignment(.center)
             }
             .padding(15)
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
             .padding()
         }
         .frame(maxWidth: .infinity,maxHeight: .infinity,alignment: .top)
         SensorCalibrationButtons()
     }
 }
}
