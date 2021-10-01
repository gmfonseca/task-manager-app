package br.com.gmfonseca.taskmanager.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    private val taskViewModel by lazy { TaskViewModelImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksList(taskViewModel)
        }
    }

    override fun onResume() {
        super.onResume()

        taskViewModel.beginRoutine(applicationContext)
    }
}
