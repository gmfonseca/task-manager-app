package br.com.gmfonseca.taskmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.gmfonseca.taskmanager.R
import br.com.gmfonseca.taskmanager.shared.domain.Task

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TaskHolder>() {

    private var content: List<Task> = emptyList()
    private var status: Status = Status.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_card, parent, false)

        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = content[position]

        holder.title.text = task.title
        holder.description.text = task.description
        holder.completeButton.setOnClickListener {
            Toast.makeText(it.context, "clicked", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int = if (status == Status.SUCCESS) {
        content.size
    } else {
        0
    }

    fun setContent(values: List<Task>?) {
        when {
            values == null -> {
                status = Status.ERROR
                content = emptyList()
            }
            values.isNotEmpty() -> {
                status = Status.SUCCESS
                content = values
            }
            else -> {
                status = Status.EMPTY
                content = values
            }
        }

        notifyDataSetChanged()
    }

    inner class TaskHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textView_taskCard_title)
        val description: TextView = view.findViewById(R.id.textView_taskCard_description)
        val completeButton: Button = view.findViewById(R.id.button_taskCard_complete)
    }

    enum class Status {
        LOADING, SUCCESS, EMPTY, ERROR
    }
}
