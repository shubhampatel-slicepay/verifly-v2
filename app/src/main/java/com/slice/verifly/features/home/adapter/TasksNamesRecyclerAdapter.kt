package com.slice.verifly.features.home.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.slice.verifly.R
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.utility.Constants
import kotlinx.android.synthetic.main.item_task_name.view.*

class TasksNamesRecyclerAdapter(
    private val tasksNamesList: List<TaskDocuments>
) : RecyclerView.Adapter<TasksNamesRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_name, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasksNamesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasksNamesList[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(task: TaskDocuments) {
            var drawable: Drawable? = null
            when (task.taskStatus) {
                Constants.NEW_STATUS -> {
                    drawable = ContextCompat.getDrawable(
                        itemView.iv_taskStatus.context,
                        R.drawable.ic_outlined_new_status
                    )
                }

                Constants.ONGOING_STATUS -> {
                    drawable = ContextCompat.getDrawable(
                        itemView.iv_taskStatus.context,
                        R.drawable.ic_outlined_ongoing_status
                    )
                }

                Constants.COMPLETED_STATUS -> {
                    drawable = ContextCompat.getDrawable(
                        itemView.iv_taskStatus.context,
                        R.drawable.ic_outlined_completed_status
                    )
                }
            }
            drawable?.let {
                itemView.iv_taskStatus.setImageDrawable(it)
            }
            itemView.tv_taskName.text = task.taskName
        }
    }
}