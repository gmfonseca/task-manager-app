package br.com.gmfonseca.taskmanager.app.ui.contracts

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract

class StartCameraForResult : ActivityResultContract<Unit, Bitmap?>() {

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bitmap? {
        return if (resultCode == ComponentActivity.RESULT_OK) {
            intent?.extras?.get("data") as? Bitmap
        } else {
            null
        }
    }
}
