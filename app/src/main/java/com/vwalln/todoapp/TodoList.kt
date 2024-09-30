package com.vwalln.todoapp

data class TodoList (
    val title: String,
    val items: MutableList<Item>
)