package com.slice.verifly.features.home.enums

import android.content.Context
import com.slice.verifly.features.home.base.BaseUiComponent
import com.slice.verifly.features.home.communicator.TaskFormOptions
import com.slice.verifly.features.home.communicator.UiComponentCommunicator
import com.slice.verifly.features.home.ui.OsvUiComponent
import com.slice.verifly.models.tasks.TaskDocuments

enum class TaskForm(taskId: String): TaskFormOptions {

    OPS0007("ops0007") {
        private var uiComponent: OsvUiComponent? = null

        override fun showForm(
            context: Context,
            task: TaskDocuments,
            isEditable: Boolean,
            communicator: UiComponentCommunicator?
        ): BaseUiComponent? {
            uiComponent = OsvUiComponent.newInstance(
                context = context,
                taskDoc = task,
                isEditable = isEditable,
                communicator = communicator
            )
            return uiComponent
        }

        override fun upload(reqCode: Int, filePath: String) {
            uiComponent?.upload(reqCode, filePath)
        }

        override fun submitForm() {
            if (uiComponent?.validate() == true) {
                uiComponent?.submit()
            }
        }
    },

    OPS0006("ops0006") {
        override fun showForm(
            context: Context,
            task: TaskDocuments,
            isEditable: Boolean,
            communicator: UiComponentCommunicator?
        ): BaseUiComponent? {
            return null
        }

        override fun submitForm() {

        }
    },

    OPS0005("ops0005") {
        override fun showForm(
            context: Context,
            task: TaskDocuments,
            isEditable: Boolean,
            communicator: UiComponentCommunicator?
        ): BaseUiComponent? {
            return null
        }

        override fun submitForm() {

        }
    },

    OPS0004("ops0004") {
        override fun showForm(
            context: Context,
            task: TaskDocuments,
            isEditable: Boolean,
            communicator: UiComponentCommunicator?
        ): BaseUiComponent? {
            return null
        }

        override fun submitForm() {

        }
    },

    OPS0003("ops0003") {
        override fun showForm(
            context: Context,
            task: TaskDocuments,
            isEditable: Boolean,
            communicator: UiComponentCommunicator?
        ): BaseUiComponent? {
            return null
        }

        override fun submitForm() {

        }
    },

    OPS0002("ops0002") {
        override fun showForm(
            context: Context,
            task: TaskDocuments,
            isEditable: Boolean,
            communicator: UiComponentCommunicator?
        ): BaseUiComponent? {
            return null
        }

        override fun submitForm() {

        }
    },

    OPS0001("ops0001") {
        override fun showForm(
            context: Context,
            task: TaskDocuments,
            isEditable: Boolean,
            communicator: UiComponentCommunicator?
        ): BaseUiComponent? {
            return null
        }

        override fun submitForm() {

        }
    }
}