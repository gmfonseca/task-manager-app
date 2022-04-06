package br.com.gmfonseca.taskmanager.app.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.gmfonseca.taskmanager.app.contracts.StartCameraForResult
import br.com.gmfonseca.taskmanager.app.ui.screens.task.create.CreateTaskFormScreen
import br.com.gmfonseca.taskmanager.app.ui.screens.task.create.CreatingTaskScreen
import br.com.gmfonseca.taskmanager.app.ui.screens.task.details.TaskDetailsScreen
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.TasksListScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class TaskActivity : ComponentActivity() {
    private val taskViewModel by viewModel<TaskViewModel>()

    private val startCameraForResult = registerForActivityResult(StartCameraForResult()) {
        onPhotoResult(it ?: return@registerForActivityResult)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = NAV_TASKS_LIST) {
                composable(NAV_TASKS_LIST) {
                    TasksListScreen(
                        taskViewModel,
                        onTaskCardClick = {
                            if (it.isCompleted) {
                                navController.navigate(NAV_TASK_DETAILS)
                                taskViewModel.selectTask(it)
                            } else {
                                taskViewModel.completingTask = it
                                startCameraForResult.launch(Unit)
                            }
                        },
                        onFabClicked = {
                            navController.navigate(NAV_CREATE_TASK)
                        }
                    )
                }

                composable(NAV_TASK_DETAILS) {
                    TaskDetailsScreen(
                        taskViewModel = taskViewModel,
                        onBackPress = navController::popBackStack
                    )
                }

                composable(NAV_CREATE_TASK) {
                    CreateTaskFormScreen(
                        taskViewModel,
                        onBackPress = {
                            taskViewModel.clearFormState()
                            navController.popBackStack()
                        },
                        onCreatePress = {
                            navController.navigate(NAV_CREATING_TASK)
                            taskViewModel.createTask(
                                onError = navController::popBackStack,
                                onSuccess = {
                                    taskViewModel.clearFormState()
                                    navController.navigate(NAV_TASKS_LIST) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                    )
                }

                composable(NAV_CREATING_TASK) {
                    CreatingTaskScreen()
                }
            }
        }

        taskViewModel.beginRoutine(applicationContext)
    }

    private fun onPhotoResult(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val bytes = stream.toByteArray().also { bitmap.recycle() }

        taskViewModel.completeTask(bytes, applicationContext)
    }

    private companion object {
        const val NAV_TASKS_LIST = "tasklist"
        const val NAV_TASK_DETAILS = "taskdetails"
        const val NAV_CREATE_TASK = "createtask"
        const val NAV_CREATING_TASK = "creatingtask"
    }
}
