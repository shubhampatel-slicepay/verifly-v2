package com.slice.verifly.features.home.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.slice.verifly.R
import com.slice.verifly.features.home.communicator.TasksRecyclerAdapterCallback
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.utility.Constants
import kotlinx.android.synthetic.main.item_task.view.*

class TasksRecyclerAdapter(
    data: List<TaskDocuments?>,
    private val listener: TasksRecyclerAdapterCallback
): RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder>() {

    // Properties

    private var data = data.toMutableList()

    // Implementations

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(itemView = view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position]?.let {
            holder.bind(it)
        }
        holder.itemView.setOnClickListener {
            listener.onTaskSelected(data[position])
        }
    }

    // ViewHolder

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(t: TaskDocuments) {
            var drawable: Drawable? = null
            var status: String? = null
            when {
                t.taskStatus?.contains(Constants.NEW_STATUS) == true -> {
                    drawable = ContextCompat.getDrawable(
                        itemView.iv_taskStatusImage.context,
                        R.drawable.ic_filled_new_status
                    )
                    status = itemView.tv_taskStatusText.context.getString(R.string.task_new_status)
                    itemView.tv_taskStatusText.setTextColor(Color.parseColor("#F2D923"))
                }
                t.taskStatus?.contains(Constants.ONGOING_STATUS) == true -> {
                    drawable = ContextCompat.getDrawable(
                        itemView.iv_taskStatusImage.context,
                        R.drawable.ic_filled_ongoing_status
                    )
                    status = itemView.tv_taskStatusText.context.getString(R.string.task_ongoing_status)
                    itemView.tv_taskStatusText.setTextColor(Color.parseColor("#FF3333"))
                }
                t.taskStatus?.contains(Constants.COMPLETED_STATUS) == true -> {
                    drawable = ContextCompat.getDrawable(
                        itemView.iv_taskStatusImage.context,
                        R.drawable.ic_filled_completed_status
                    )
                    status = itemView.tv_taskStatusText.context.getString(R.string.task_completed_status)
                    itemView.tv_taskStatusText.setTextColor(Color.parseColor("#00DB00"))
                }
            }
            drawable?.let {
                itemView.iv_taskStatusImage.setImageDrawable(it)
            }
            status?.let {
                itemView.tv_taskStatusText.text = it
            }
            itemView.tv_taskName.text = t.taskName
            itemView.tv_taskDue.text = t.scheduleDate.toString()
        }
    }

    // Communications

    fun notifyDataChanged(newData: List<TaskDocuments?>) {
        data.clear()
        data = newData.toMutableList()
        notifyDataSetChanged()
    }
}