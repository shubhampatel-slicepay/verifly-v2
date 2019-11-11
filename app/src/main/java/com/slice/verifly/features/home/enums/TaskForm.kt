package com.slice.verifly.features.home.enums

import com.slice.verifly.features.home.TaskFormOptions
import com.slice.verifly.models.tasks.TaskDocuments

enum class TaskForm(taskId: String): TaskFormOptions {

    OPS0007("ops0007") {
        override fun loadEditableForm(task: TaskDocuments) {

        }

        override fun loadUnEditableForm(task: TaskDocuments) {

        }

    },

    OPS0006("ops0006") {
        override fun loadEditableForm(task: TaskDocuments) {

        }

        override fun loadUnEditableForm(task: TaskDocuments) {

        }

    },

    OPS0005("ops0005") {
        override fun loadEditableForm(task: TaskDocuments) {

        }

        override fun loadUnEditableForm(task: TaskDocuments) {

        }

    },

    OPS0004("ops0004") {
        override fun loadEditableForm(task: TaskDocuments) {

        }

        override fun loadUnEditableForm(task: TaskDocuments) {

        }

    },

    OPS0003("ops0003") {
        override fun loadEditableForm(task: TaskDocuments) {

        }

        override fun loadUnEditableForm(task: TaskDocuments) {

        }

    },

    OPS0002("ops0002") {
        override fun loadEditableForm(task: TaskDocuments) {

        }

        override fun loadUnEditableForm(task: TaskDocuments) {

        }

    },

    OPS0001("ops0001") {
        override fun loadEditableForm(task: TaskDocuments) {

        }

        override fun loadUnEditableForm(task: TaskDocuments) {

        }

    }
}