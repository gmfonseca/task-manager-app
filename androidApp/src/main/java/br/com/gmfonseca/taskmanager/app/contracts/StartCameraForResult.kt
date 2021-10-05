package br.com.gmfonseca.taskmanager.app.contracts

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract

class StartCameraForResult : ActivityResultContract<Intent, Bitmap?>() {

    override fun createIntent(context: Context, input: Intent?): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    }

    override fun parseResult(resultCode: Int, data: Intent?): Bitmap? {
        return if (resultCode == ComponentActivity.RESULT_OK) {
            return data?.extras?.get("data") as? Bitmap
        } else {
            null
        }
    }
}