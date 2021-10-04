//
//  TasksList.swift
//  iosApp
//
//  Created by Gabriel Fonseca on 26/09/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TasksListView: View {
    
    @ObservedObject var taskListViewModel: TaskListViewModel
    
    @State private var sourceType: UIImagePickerController.SourceType = .photoLibrary
    @State private var isImagePickerDisplay = false

    var body: some View {

        GeometryReader { view in
            if let tasks = taskListViewModel.tasks, !tasks.isEmpty {
                List(tasks, id: \.self.id) { task in
                    TaskCardView(
                        item: TaskCardItem(
                            title: task.title,
                            description: task.description_,
                            isCompleted: task.isCompleted
                        ),
                        onClick: { _ in
                            isImagePickerDisplay.toggle()
                        }
                    )
                    .sheet(isPresented: self.$isImagePickerDisplay) {
                        ImagePickerView(sourceType: self.sourceType) { selectedImage in

                            taskListViewModel.completeTask(task: task, image: selectedImage)
                        }
                    }
                }
            } else {
                Text("No tasks available")
                    .frame(
                        maxWidth: view.size.width,
                        maxHeight: view.size.height
                    )
            }
        }
        .onAppear { taskListViewModel.startTasksRoutine() }
        .onDisappear {  taskListViewModel.stopTasksRoutine() }
    }
}

struct TasksList_Previews: PreviewProvider {
    static var previews: some View {
        TasksListView(taskListViewModel: TaskListViewModel())
    }
}
