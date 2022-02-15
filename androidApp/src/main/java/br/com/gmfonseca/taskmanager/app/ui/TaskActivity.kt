package br.com.gmfonseca.taskmanager.app.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.gmfonseca.taskmanager.app.contracts.StartCameraForResult
import br.com.gmfonseca.taskmanager.app.ui.screens.tasklist.TasksListScreen
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
            TasksListScreen(
                taskViewModel,
                onTaskCardClick = {
                    if (it.isCompleted) {
                        Toast.makeText(applicationContext, "Available soon!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        taskViewModel.completingTask = it
                        startCameraForResult.launch(Unit)
                    }
                }
            )
        }

        taskViewModel.beginRoutine(applicationContext)
    }

    private fun onPhotoResult(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val bytes = stream.toByteArray().also { bitmap.recycle() }

        taskViewModel.completeTask(bytes, applicationContext)
    }
}
