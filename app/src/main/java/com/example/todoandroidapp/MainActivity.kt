package com.example.todoandroidapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.DatePickerDialog
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var todoAdapter : TodoAdapter
    private var lastSelectedYear: Int = -1
    private var lastSelectedMonth: Int = -1
    private var lastSelectedDay: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val rvTodoItems = findViewById<RecyclerView>(R.id.rvToDoItems)
        todoAdapter = TodoAdapter(mutableListOf())
        Log.d("Todo123", todoAdapter.toString())
        rvTodoItems.adapter = todoAdapter

        rvTodoItems.layoutManager = LinearLayoutManager(this)
        val btnAddTodo = findViewById<Button>(R.id.btnAddTodo)
        val btnDeleteDoneTodo = findViewById<Button>(R.id.btnDeleteDoneTodo)
        val etTodoTitle = findViewById<EditText>(R.id.etTodoTitle)
        btnAddTodo.setOnClickListener{
            val todoTitle = etTodoTitle.text.toString()
            val tvDate = findViewById<TextView>(R.id.tvDate)
            if(todoTitle.isNotEmpty()){
                val todo = Todo(todoTitle, false, tvDate.text.toString())
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
                setToCurrentDate()
            }
        }
        btnDeleteDoneTodo.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
        setToCurrentDate()
        val btnShowCalendar = findViewById<Button>(R.id.btnShowCalendar)
        btnShowCalendar.setOnClickListener {
            showDatePickerDialog()
        }


    }
    private fun showDatePickerDialog() {
        val tvDate = findViewById<TextView>(R.id.tvDate)

        if (lastSelectedYear == -1) { // This means no date was previously selected
            setToCurrentDate()
        }
        val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
            tvDate.text=selectedDate

            lastSelectedYear = year
            lastSelectedMonth = monthOfYear
            lastSelectedDay = dayOfMonth
        }, lastSelectedYear, lastSelectedMonth, lastSelectedDay)

        // Show DatePickerDialog
        dpd.show()
    }
    private fun setToCurrentDate(){
        val c = Calendar.getInstance()
        lastSelectedYear = c.get(Calendar.YEAR)
        lastSelectedMonth = c.get(Calendar.MONTH)
        lastSelectedDay = c.get(Calendar.DAY_OF_MONTH)
        val tvDate = findViewById<TextView>(R.id.tvDate)
        val curDate = "$lastSelectedDay/${lastSelectedMonth + 1}/$lastSelectedYear"
        tvDate.text= curDate
    }

}