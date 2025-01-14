/// Copyright (c) 2022 Razeware LLC
///
/// Permission is hereby granted, free of charge, to any person obtaining a copy
/// of this software and associated documentation files (the "Software"), to deal
/// in the Software without restriction, including without limitation the rights
/// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
/// copies of the Software, and to permit persons to whom the Software is
/// furnished to do so, subject to the following conditions:
///
/// The above copyright notice and this permission notice shall be included in
/// all copies or substantial portions of the Software.
///
/// Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
/// distribute, sublicense, create a derivative work, and/or sell copies of the
/// Software in any work that is designed, intended, or marketed for pedagogical or
/// instructional purposes related to programming, coding, application development,
/// or information technology.  Permission for such use, copying, modification,
/// merger, publication, distribution, sublicensing, creation of derivative works,
/// or sale is expressly withheld.
///
/// This project and source code may use libraries or frameworks that are
/// released under various Open-Source licenses. Use of those libraries and
/// frameworks are governed by their own individual licenses.
///
/// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
/// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
/// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
/// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
/// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
/// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
/// THE SOFTWARE.

import SwiftUI
import shared

struct FindMeeting: View {
    @EnvironmentObject private var timezoneItems: TimezoneItems
    private let timezoneHelper = TimeZoneHelperImpl()
    @State private var meetingHours: [Int] = []
    @State private var showHoursDialog = false
    @State private var startDate = Calendar.current.date(bySettingHour: 8, minute: 0, second: 0, of: Date())!
    @State private var endDate = Calendar.current.date(bySettingHour: 17, minute: 0, second: 0, of: Date())!
    
    var body: some View {
        NavigationView {
            VStack {
                Spacer()
                    .frame(height: 8)
                Form {
                    Section(header: Text("Time Range")) {
                        DatePicker("Start Time", selection: $startDate, displayedComponents: .hourAndMinute)
                        DatePicker("End Time", selection: $endDate, displayedComponents: .hourAndMinute)
                    }
                    Section(header: Text("Time Zones")) {
                        ForEach(Array(timezoneItems.selectedTimezones), id: \.self) {  timezone in
                            HStack {
                                Text(timezone)
                                Spacer()
                            }
                        }
                    }
                } // Form
                Spacer()
                Button(action: {
                    meetingHours.removeAll()
                    let startHour = Calendar.current.component(.hour, from: startDate)
                    let endHour = Calendar.current.component(.hour, from: endDate)
                    let hours = timezoneHelper.search(
                        startHour: Int32(startHour),
                        endHour: Int32(endHour),
                        timezoneStrings: Array(timezoneItems.selectedTimezones))
                    let hourInts = hours.map { kotinHour in
                        Int(truncating: kotinHour)
                    }
                    meetingHours += hourInts
                    showHoursDialog = true
                }, label: {
                    Text("Search")
                        .foregroundColor(Color.black)
                })
                Spacer()
                    .frame(height: 8)
            } // VStack
            .navigationTitle("Find Meeting Time")
            .sheet(isPresented: $showHoursDialog) {
                HourSheet(hours: $meetingHours)
            }
        } // NavigationView
    }
}

struct FindMeeting_Previews: PreviewProvider {
    static var previews: some View {
        FindMeeting()
    }
}
