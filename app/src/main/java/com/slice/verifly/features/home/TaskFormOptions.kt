package com.slice.verifly.features.home

import com.slice.verifly.models.tasks.TaskDocuments

interface TaskFormOptions {

    fun loadEditableForm(task: TaskDocuments)

    fun loadUnEditableForm(task: TaskDocuments)
}