//
//  CommonView.swift
//  iosApp
//
//  Created by KBS on 2/27/26.
//

import SwiftUI
struct BlackThumbToggle: View {
    @Binding var isOn: Bool
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 20)
                .fill(isOn ?  Color(argb: 0xff24A19D) : Color.gray.opacity(0.4))
                .frame(width: 60, height: 32)
            Circle()
                .fill(Color.black)
                .frame(width: 26, height: 26)
                .offset(x: isOn ? 14 : -14)
                .animation(.easeInOut(duration: 0.2), value: isOn)
        }
        .onTapGesture {
            isOn.toggle()
        }
    }
}
struct SensorCalibrationButtons: View {
    var body: some View {
        HStack(spacing: 20) {
            Button(action: {
            }) {
                Text("Discard")
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 50)
            }
            .background(
                RoundedRectangle(cornerRadius: 15)
                    .stroke(Color.gray.opacity(0.3), lineWidth: 1)
            )
            Button(action: {
            }) {
                Text("Save")
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 50)
            }
            .background(
                RoundedRectangle(cornerRadius: 15)
                    .fill(Color(argb: 0xff24A19D))
            )
        }
        .frame(maxWidth: .infinity,maxHeight: .infinity,alignment: .bottom)
        .padding()
    }
}
