package com.example.todoandroidapp

data class Todo(
    val title: String = "",
    var isChecked: Boolean = false,
    var date: String = "",
)