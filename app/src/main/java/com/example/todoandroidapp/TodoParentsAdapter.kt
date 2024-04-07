package com.example.todoandroidapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class TodoParentsAdapter : RecyclerView.Adapter<TodoParentsAdapter.TodoParentsViewHolder>() {
    private var todoParentsList: MutableList<TodoParents> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addTodo(todo: Todo) {
        val todoParentIndex = todoParentsList.indexOfFirst { it.date == todo.date }
        if (todoParentIndex >= 0) {
            todoParentsList[todoParentIndex].TodoList.add(todo)
        } else {
            todoParentsList.add(TodoParents(todo.date, mutableListOf(todo)))
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteDoneTodos() {
        var changesMade = false
        val iterator = todoParentsList.iterator()
        while (iterator.hasNext()) {
            val todoParent = iterator.next()

            // Remove all completed todos from this TodoParent's TodoList
            val todosToRemove = todoParent.TodoList.filter { it.isChecked }
            if (todosToRemove.isNotEmpty()) {
                todoParent.TodoList.removeAll(todosToRemove)
                changesMade = true
            }

            // If this TodoParent now has an empty TodoList, remove it from todoParentsList
            if (todoParent.TodoList.isEmpty()) {
                iterator.remove()
                changesMade = true
            }
        }

        // Only notify the adapter of data set changes if we actually made any changes
        if (changesMade) {
            notifyDataSetChanged()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoParentsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_main, parent, false)
        return TodoParentsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoParentsViewHolder, position: Int) {
        val todoParent = todoParentsList[position]
        holder.bind(todoParent)
    }

    override fun getItemCount(): Int = todoParentsList.size

    class TodoParentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTodoDate: TextView = itemView.findViewById(R.id.tvTodoDate)
        private val rvToDoItemsChild: RecyclerView = itemView.findViewById(R.id.rvToDoItemsChild)

        fun bind(todoParent: TodoParents) {
            tvTodoDate.text = todoParent.date

            val todoAdapter = TodoAdapter(todoParent.TodoList)
            rvToDoItemsChild.layoutManager = LinearLayoutManager(itemView.context)
            rvToDoItemsChild.adapter = todoAdapter
        }
    }
}