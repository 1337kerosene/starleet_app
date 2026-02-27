//
//  SettingView.swift
//  iosApp
//
//  Created by KBS on 2/27/26.
//

import SwiftUI
struct SettingView: View {
@State private var isSilent = true
var body: some View {
    ZStack {
     VStack(alignment:.leading,spacing: 30){
         Text("Settings")
             .font(.headingManron)
             .foregroundStyle(Color.white)
             .padding(.horizontal, 20)
             .padding(.vertical,10)
         HStack(spacing: 16) {
             Image("ic_bell")
                 .font(.system(size: 26))
                 .foregroundColor(.white)
             VStack(alignment: .leading, spacing: 4) {
                 Text("Silent Mode")
                     .font(.system(size: 20, weight: .semibold))
                     .foregroundColor(.white)
                 Text(isSilent ? "On" : "Off")
                     .font(.system(size: 14))
                     .foregroundColor(.gray)
             }
             Spacer()
             BlackThumbToggle(isOn: $isSilent)
                 .tint(Color(argb: 0xff24A19D))
         }
         .padding(20)
         .frame(maxWidth: .infinity,maxHeight: 70)
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
         .padding(.horizontal, 20)
         Spacer()
         SliderView()
     }
 }
 .frame(maxWidth: .infinity,maxHeight: .infinity, alignment: .top)
}
}
struct SliderView: View {
 @State private var sliderOffset: CGFloat = 0
  @State private var textOpacity: Double = 1.0
 let swipeThresholdRatio: CGFloat = 0.8
 var body: some View {
 GeometryReader { geometry in
     let maxWidth = geometry.size.width

         ZStack(alignment: .leading) {
             RoundedRectangle(cornerRadius: 35)
                 .fill(
                     LinearGradient(
                         colors: [
                             Color(argb: 0xff454545),
                             Color(argb: 0xff333333)
                         ],
                         startPoint: .leading,
                         endPoint: .trailing
                     )
                 )
                 .frame(height: 60)
             RoundedRectangle(cornerRadius: 35)
                 .fill(Color(argb:0xffDE2424))
                 .padding(2)
                 .frame(width: min(sliderOffset + 60, geometry.size.width),height: 60)
             Text("Slide to delete all data")
                 .foregroundColor(Color(argb: 0xffFFA9A9))
                 .font(.bodyManron)
                 .opacity(textOpacity)
                 .frame(maxWidth: .infinity)
             Image("ic_slide")
                 .padding(.leading,25)
                 .offset(x: sliderOffset)
                 .buttonStyle(.plain)
         }
         .clipShape(RoundedRectangle(cornerRadius: 35))
         .gesture(
             DragGesture()
                 .onChanged { value in
                     let drag = max(0, min(value.translation.width, maxWidth))
                     sliderOffset = drag
                     textOpacity = 1 - drag / maxWidth
                 }
                 .onEnded { _ in
                     if sliderOffset > maxWidth * swipeThresholdRatio {
                         withAnimation(.spring(response: 0.10, dampingFraction: 0.5)) {
                             sliderOffset = maxWidth
                             textOpacity = 0
                         }
                     } else {
                         withAnimation(.easeOut(duration: 0.25  )) {
                             sliderOffset = 0
                             textOpacity = 1
                         }
                     }
                 }
         )
     
 }
 .frame(height: 60)
 .padding(30)
}
}

