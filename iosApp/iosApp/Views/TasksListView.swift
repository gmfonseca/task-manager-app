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

    var body: some View {
        GeometryReader { view in
            if let tasks = taskListViewModel.tasks, !tasks.isEmpty {
                List(tasks, id: \.self.id) { task in
                    TaskCardView(
                        item: TaskCardItem(
                            title: task.title,
                            description: task.description_,
                            isCompleted: task.isCompleted
                        )
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
        .onDisappear { taskListViewModel.stopTasksRoutine() }
    }
}

struct TasksList_Previews: PreviewProvider {
    static var previews: some View {
        TasksListView(taskListViewModel: TaskListViewModel())
    }
}

class TaskListViewModel: ObservableObject {
    
    @Published var tasks: [Task]?

    private var timer: Timer?

    func startTasksRoutine() {
        timer = Timer.scheduledTimer(
            timeInterval: 10,
            target: self,
            selector: #selector(TaskListViewModel.fetchTasks),
            userInfo: nil,
            repeats: true
        )
    }

    func stopTasksRoutine() {
        if let timer = timer {
            timer.invalidate()
            self.timer = nil
        }
    }

    @objc func fetchTasks() {
        TaskClientKt.listTasks { result in
            if let tasks = result.getOrNull() as? [Task] {
                self.tasks = tasks
            } else {
                if let e = result.exceptionOrNull() {
                    print("Failed: \(e)")
                } else {
                    print("Unkown failure")
                }
            }
        }
    }
}
