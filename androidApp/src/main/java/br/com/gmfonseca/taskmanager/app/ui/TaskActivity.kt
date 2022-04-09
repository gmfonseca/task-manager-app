package br.com.gmfonseca.taskmanager.app.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.gmfonseca.taskmanager.app.contracts.StartCameraForResult
import br.com.gmfonseca.taskmanager.app.ui.components.feedback.SnackbarNotificationData
import br.com.gmfonseca.taskmanager.app.ui.screens.task.create.CreateTaskFormScreen
import br.com.gmfonseca.taskmanager.app.ui.screens.task.create.CreatingTaskScreen
import br.com.gmfonseca.taskmanager.app.ui.screens.task.details.TaskDetailsScreen
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.TasksListScreen
import br.com.gmfonseca.taskmanager.app.ui.screens.task.list.model.Status
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

            NavHost(
                navController = navController,
                startDestination = "$NAV_TASKS_LIST?status={status}&taskId={taskId}"
            ) {
                composable(
                    route = "$NAV_TASKS_LIST?status={status}&taskId={taskId}",
                    arguments = listOf(
                        navArgument(name = "status") { nullable = true; defaultValue = null },
                        navArgument(name = "taskId") { nullable = true; defaultValue = null },
                    )
                ) { backStackEntry ->
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
                        },
                        snackbarData = backStackEntry.arguments?.run {
                            val status = Status.valueOf(getString("status", Status.NONE.name))
                            val taskId = getString("taskId", "?")

                            when (status) {
                                Status.SUCCEED_COMPLETE -> SnackbarNotificationData.Success("Successfully completed the task #$taskId")
                                Status.ERROR_COMPLETE -> SnackbarNotificationData.Failure("Failed to complete the task #$taskId")
                                Status.SUCCEED_CREATE -> SnackbarNotificationData.Success("Successfully created the task #$taskId")
                                Status.NONE -> null
                            }
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
                                onSuccess = { status, taskId ->
                                    taskViewModel.clearFormState()
                                    navController.navigate("$NAV_TASKS_LIST?status=${status}&taskId=${taskId}") {
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
