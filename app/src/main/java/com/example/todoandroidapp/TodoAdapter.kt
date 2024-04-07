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
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class TodoAdapter (
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo : Todo){
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteDoneTodos(){
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle:TextView, isChecked:Boolean){
        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]

        val tvTodoTitle = holder.itemView.findViewById<TextView>(R.id.tvTodoTitle)
//        val tvTodoItemDate = holder.itemView.findViewById<TextView>(R.id.tvTodoItemDate)
        val cbDone = holder.itemView.findViewById<CheckBox>(R.id.cbDone)

        tvTodoTitle.text = curTodo.title
//        tvTodoItemDate.text = curTodo.date
        cbDone.setOnCheckedChangeListener(null)
        cbDone.isChecked = curTodo.isChecked
        toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
        cbDone.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikeThrough(tvTodoTitle, isChecked)
            curTodo.isChecked = !curTodo.isChecked}
    }

    override fun getItemCount(): Int {
        return todos.size
    }


}