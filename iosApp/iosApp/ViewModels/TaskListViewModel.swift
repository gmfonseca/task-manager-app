//
//  TaskListViewModel.swift
//  iosApp
//
//  Created by Gabriel Fonseca on 03/10/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

class TaskListViewModel: ObservableObject {
    
    @Published var tasks: [Task]?

    private var routineFlow: Closeable?

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
    
    func completeTask(task: Task, image: UIImage) {
        guard let data = image.pngData() else { return }
        let params = CompleteTaskUseCaseParams(id: task.id, fileBytes: ImageParsersKt.toByteArray(data))

        CompleteTaskUseCaseImpl().invoke(params: params)
            .watch { result in
                if result!.getOrNull() == true {
                    self.tasks = self.tasks?.filter { it in
                        it.id != task.id
                    }
                } else {
                    if let e = result!.exceptionOrNull() {
                        print("Failed: \(e)")
                    } else {
                        print("Unkown failure")
                    }
                }
            }
    }
}
