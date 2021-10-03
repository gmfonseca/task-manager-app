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
                        onClick: { taskCardView in
                            if taskListViewModel.currentTaskId == nil {
                                isImagePickerDisplay.toggle()
                                taskListViewModel.currentTaskId = task.id
                            }
                        }
                    )
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
        .sheet(isPresented: self.$isImagePickerDisplay) {
            ImagePickerView(sourceType: self.sourceType) { selectedImage in
                taskListViewModel.completeTask(image: selectedImage)
            }
        }
    }
}

struct TasksList_Previews: PreviewProvider {
    static var previews: some View {
        TasksListView(taskListViewModel: TaskListViewModel())
    }
}

class TaskListViewModel: ObservableObject {
    
    @Published var tasks: [Task]?
    var currentTaskId: String? = nil

    private var routineFlow: Ktor_ioCloseable?

    func startTasksRoutine() {
        if(routineFlow == nil) {
            routineFlow = fetchTasks()
        }
    }

    func stopTasksRoutine() {
        if let flow = routineFlow {
            flow.close()
            self.routineFlow = nil
        }
    }

    private func fetchTasks() -> Closeable {
        return FetchRemoteTasksRoutineUseCaseImpl().invoke()
            .watch { result in
                if let tasks = result!.getOrNull() as? [Task] {
                    self.tasks = tasks
                } else {
                    if let e = result!.exceptionOrNull() {
                        print("Failed: \(e)")
                    } else {
                        print("Unkown failure")
                    }
                }
            }
    }
    
    func completeTask(image: UIImage) {
        guard let taskId = currentTaskId else { return }
        guard let data = image.pngData() else { return }
        let params = CompleteTasksRoutineUseCaseParams(id: taskId, fileBytes: ImageKt.toByteArray(data))

        currentTaskId = nil

        CompleteTasksRoutineUseCaseImpl().invoke(params: params).watch { _ in
        }
    }
}
