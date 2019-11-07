package com.slice.verifly.features.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slice.verifly.R
import com.slice.verifly.features.home.communicator.UsersTasksRecyclerAdapterCallback
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.features.home.models.UsersTasksData
import kotlinx.android.synthetic.main.item_user_task.view.*

class UsersTasksRecyclerAdapter(
    private val data: List<UsersTasksData?>,
    private val listener: UsersTasksRecyclerAdapterCallback
): RecyclerView.Adapter<UsersTasksRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_task, parent, false)
        return ViewHolder(itemView = view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data[position]?.let { task ->
            holder.bind(task)
        }

        holder.itemView.cl_userTaskItemContainer.setOnClickListener {
            listener.onUserTaskSelected(data[position])
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var adapter: TasksNamesRecyclerAdapter? = null

        fun bind(t: UsersTasksData) {
            if (t.taskDocs?.isNullOrEmpty() == false) {
                val tasksList = t.taskDocs!!

                // user image
                val userImage = tasksList[0].userSelfie
                userImage?.let {
                    Glide.with(itemView.civ_userImage.context).load(it).into(itemView.civ_userImage)
                } ?: run {
                    val image = itemView.civ_userImage.context.resources.getDrawable(R.drawable.ic_account_circle)
                    itemView.civ_userImage.setImageDrawable(image)
                }

                // user name
                val userName = tasksList[0].userName
                userName?.let {
                    itemView.tv_userName.text = it
                } ?: run {
                    itemView.tv_userName.text = "--"
                }

                // user college
                val userCollege = tasksList[0].userCollege
                userName?.let {
                    itemView.tv_userCollege.text = it
                } ?: run {
                    itemView.tv_userCollege.text = "--"
                }

                // task names and status
                populateRecycler(tasksList)

                // counts
                itemView.tv_pendingTasksCount.text = tasksList.size.toString()
            }
        }

        private fun populateRecycler(tasksList: List<TaskDocuments>) {
            if (adapter == null) {
                adapter = TasksNamesRecyclerAdapter(tasksList)
            }
            with(itemView.rv_tasksNamesList) {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = this@ViewHolder.adapter
            }
        }
    }
}