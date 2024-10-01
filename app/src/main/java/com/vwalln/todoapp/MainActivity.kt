package com.vwalln.todoapp

import android.icu.text.Transliterator.Position
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vwalln.todoapp.databinding.ActivityMainBinding
import com.vwalln.todoapp.databinding.DialogAddListBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var todoListAdapter : TodoListAdapter

    private val todoLists = mutableListOf<TodoList>()

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)

        //View binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialisiere den adapter und setze ihn auf den recyclerview

        todoListAdapter = TodoListAdapter(todoLists,
            { todoList ->
            //handle item click
        },
            { todoList, position ->
                showDeleteConfirmationDialog(todoList, position)
            }
        )

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
        }
    }

    fun addNewList(){
        val inflater = layoutInflater
        val dialogBinding = DialogAddListBinding.inflate(inflater)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Neue Liste hinzufügen")
        builder.setView(dialogBinding.root)
        builder.setPositiveButton("Hinzufügen", null)
        builder.setNegativeButton("Abbrechen", null)

        val dialog = builder.create()
        dialog.setOnShowListener{
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            positiveButton.setOnClickListener{
                val listName = dialogBinding.listNameInput.text.toString().trim()
                if (listName.isNotEmpty()){
                   val newList = TodoList(listName, mutableListOf())
                    todoListAdapter.addTodoList(newList)
                    dialog.dismiss()
                }else{
                    dialogBinding.errorMessage.visibility = View.VISIBLE
                    dialogBinding.listNameInput.requestFocus()
                }
            }
            negativeButton.setOnClickListener{
                dialog.dismiss()
            }
        }
        dialog.show()

    }

    //delete dialog und confirmation
    fun showDeleteConfirmationDialog(todoList: TodoList, position: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Liste löschen")
        builder.setMessage("Möchten Sie die Liste \"${todoList.title}\" wirklich löschen?")
        builder.setPositiveButton("Löschen"){_, _ ->
            val listName = todoLists[position].title
            todoListAdapter.removeTodoList(position)
            Toast.makeText(
                binding.root.context,
                "$listName gelöscht",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("Abbrechen", null)
        builder.show()
    }

}