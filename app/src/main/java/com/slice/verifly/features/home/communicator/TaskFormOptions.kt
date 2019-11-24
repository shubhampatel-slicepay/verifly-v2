package com.slice.verifly.features.home.communicator

import android.content.Context
import com.slice.verifly.features.home.base.BaseUiComponent
import com.slice.verifly.models.tasks.TaskDocuments

interface TaskFormOptions {

    // abstract functions for mandatory implementations by TaskForm types

    fun showForm(
        context: Context,
        task: TaskDocuments,
        isEditable: Boolean = true,
        communicator: UiComponentCommunicator? = null
    ): BaseUiComponent?

    fun submitForm()

    // default functions for optional implementations by TaskForm types
    // in order to communicate to their associate components

    fun upload(reqCode: Int, filePath: String) {}
}