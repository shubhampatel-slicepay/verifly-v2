package com.slice.verifly.features.home.communicator

import android.content.Context
import com.slice.verifly.features.home.base.BaseUiComponent
import com.slice.verifly.models.tasks.TaskDocuments

interface TaskFormOptions {

    fun showForm(
        context: Context,
        task: TaskDocuments,
        isEditable: Boolean = true
    ): BaseUiComponent?
}