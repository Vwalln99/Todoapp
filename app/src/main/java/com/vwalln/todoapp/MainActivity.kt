package com.vwalln.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vwalln.todoapp.databinding.ActivityMainBinding
import com.vwalln.todoapp.databinding.DialogAddListBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var todoListAdapter : TodoListAdapter

    private lateinit var todoLists : MutableList<TodoList>

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)

        //View binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialisiere den adapter und setze ihn auf den recyclerview

        todoListAdapter = TodoListAdapter(todoLists) { _ ->
            //handle item click
        }

        /*
        binding.recyclerViewList.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoListAdapter
        }

        //Beispieldaten
        val exampleList = TodoList("Einkaufsliste",
            mutableListOf(
                Item("Bananen", false),
                Item("Mehl", true),
                Item("Milch", false)))
        val exampleList2 = TodoList("Garten",
            mutableListOf(
                Item("Rindenmulch", false)
            ))
        todoListAdapter.addTodoList(exampleList)
        todoListAdapter.addTodoList(exampleList2)

        binding.addListButton.setOnClickListener{
            addNewList();
        }*/
    }

    fun addNewList(){
        //val inflater = layoutInflater
        //val dialogBinding = DialogAddListBinding.inflate(inflater)

    }

}