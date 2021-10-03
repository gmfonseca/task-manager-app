package br.com.gmfonseca.taskmanager.app

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    private val taskViewModel by lazy { TaskViewModelImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TasksList(
                taskViewModel,
                onClick = {
                    taskViewModel.currentTask = it
                    startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST_CODE)
                }
            )
        }

        taskViewModel.beginRoutine(applicationContext)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val bitmap = requireNotNull(data?.extras?.get("data") as? Bitmap)
            val stream = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bytes = stream.toByteArray()
            bitmap.recycle()

            taskViewModel.completeTask(bytes, applicationContext)
        }

        taskViewModel.currentTask = null
    }

    private companion object {
        const val CAMERA_REQUEST_CODE = 0x01
    }
}
