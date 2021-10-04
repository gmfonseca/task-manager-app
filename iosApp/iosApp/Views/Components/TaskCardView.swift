//
//  TaskCardView.swift
//  iosApp
//
//  Created by Gabriel Fonseca on 26/09/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TaskCardItem {
    let title: String
    let description: String
    let isCompleted: Bool
}

struct TaskCardView: View {
    let item: TaskCardItem
    let onClick: (TaskCardView) -> Void

    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(item.title).lineLimit(1).font(.system(size: 20, weight: .bold))
                Text(item.description).lineLimit(1).font(.system(size: 12))
            }

            if(!item.isCompleted) {
                Spacer()
                Button("Finish", action: { onClick(self) })
                .foregroundColor(.white)
            }
        }
        .padding(.vertical, 8)
        .padding(.horizontal, 16)
        .overlay(RoundedRectangle(cornerRadius: 4).stroke(Color.gray, lineWidth: 1))
        .background(Color(red: 0, green: 0, blue: 0, opacity: 0.27))
    }
}

struct TaskCardView_Previews: PreviewProvider {
    static var previews: some View {
        TaskCardView(
            item: TaskCardItem(title: "The title", description: "The description", isCompleted: false), onClick: {_ in }
        )
    }
}
