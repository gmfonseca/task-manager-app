package br.com.gmfonseca.taskmanager

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.gmfonseca.taskmanager.adapter.TasksAdapter
import br.com.gmfonseca.taskmanager.shared.client.listTasks

class MainActivity : AppCompatActivity() {
    private lateinit var tasksAdapter: TasksAdapter

    private val handler = Handler()
    private lateinit var repeater: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tasksAdapter = TasksAdapter()

        findViewById<RecyclerView>(R.id.recyclerView_mainActivity_tasks).apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = tasksAdapter
            setHasFixedSize(true)
        }

        init()
    }

    override fun onResume() {
        super.onResume()

        repeater.run()
    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(repeater)
    }

    private fun init() {
        repeater = kotlinx.coroutines.Runnable {
            listTasks {
                tasksAdapter.setContent(it.getOrNull())

                if (it.isFailure) {
                    Toast.makeText(
                        applicationContext, "Failed: ${it.exceptionOrNull()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            handler.postDelayed(repeater, 10000)
        }
    }
}
