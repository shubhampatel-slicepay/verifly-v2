package com.slice.verifly.features.home.enums

import android.content.Context
import com.slice.verifly.features.home.base.BaseUiComponent
import com.slice.verifly.features.home.communicator.TaskFormOptions
import com.slice.verifly.features.home.ui.OsvUiComponent
import com.slice.verifly.models.tasks.TaskDocuments

enum class TaskForm(taskId: String): TaskFormOptions {

    OPS0007("ops0007") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean): BaseUiComponent? {
            return OsvUiComponent.newInstance(context, taskDoc = task, isEditable = isEditable)
        }
    },

    OPS0006("ops0006") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean): BaseUiComponent? {
            return null
        }
    },

    OPS0005("ops0005") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean): BaseUiComponent? {
            return null
        }
    },

    OPS0004("ops0004") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean): BaseUiComponent? {
            return null
        }
    },

    OPS0003("ops0003") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean): BaseUiComponent? {
            return null
        }
    },

    OPS0002("ops0002") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean): BaseUiComponent? {
            return null
        }
    },

    OPS0001("ops0001") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean): BaseUiComponent? {
            return null
        }
    }
}