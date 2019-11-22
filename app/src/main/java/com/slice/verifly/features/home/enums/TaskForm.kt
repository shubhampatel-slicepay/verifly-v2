package com.slice.verifly.features.home.enums

import android.content.Context
import com.slice.verifly.features.home.communicator.TaskFormOptions
import com.slice.verifly.models.tasks.TaskDocuments

enum class TaskForm(taskId: String): TaskFormOptions {

    OPS0007("ops0007") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean) {

        }
    },

    OPS0006("ops0006") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean) {

        }
    },

    OPS0005("ops0005") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean) {

        }
    },

    OPS0004("ops0004") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean) {

        }
    },

    OPS0003("ops0003") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean) {

        }
    },

    OPS0002("ops0002") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean) {

        }
    },

    OPS0001("ops0001") {
        override fun showForm(context: Context, task: TaskDocuments, isEditable: Boolean) {

        }
    }
}