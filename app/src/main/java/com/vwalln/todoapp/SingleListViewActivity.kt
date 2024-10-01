package com.vwalln.todoapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vwalln.todoapp.databinding.ActivitySingleListViewBinding
import com.vwalln.todoapp.databinding.DialogAddItemBinding
import com.vwalln.todoapp.databinding.DialogAddListBinding

class SingleListViewActivity : AppCompatActivity() {
    private lateinit var todoList: TodoList
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivitySingleListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoList = intent.getParcelableExtraProvider("todoList") ?: return

        itemAdapter = ItemAdapter(todoList.items)
        binding.recyclerViewItemList.apply {
            layoutManager = LinearLayoutManager(this@SingleListViewActivity)
            adapter = itemAdapter
        }
        binding.listName.text = todoList.title

        //zurück button
        binding.backButton.setOnClickListener {
            val intent = Intent().apply {
                putExtra("updatedToDoList", todoList)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.addItemButton.setOnClickListener {
            addNewItem()
        }
        binding.deleteCompletedItemsButton.setOnClickListener {
            //TODO dialogfenster ob man wirklich alles löschen möchte
        }
    }


    //add items to todolist with add item button
    fun addNewItem() {
//dialog öffnen
        val inflater = layoutInflater
        val dialogBinding = DialogAddItemBinding.inflate(inflater)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Neues Item hinzufügen")
        builder.setView(dialogBinding.root)
        builder.setPositiveButton("Hinzufügen", null)
        builder.setNegativeButton("Abbrechen", null)

        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            positiveButton.setOnClickListener {
                val itemName = dialogBinding.itemNameInput.text.toString().trim()
                if (itemName.isNotEmpty()) {
                    val newItem = Item(itemName)
                    itemAdapter.addItem(newItem)
                    dialog.dismiss()
                } else {
                    dialogBinding.errorMessage.visibility = View.VISIBLE
                    dialogBinding.itemNameInput.requestFocus()
                }
            }
            negativeButton.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    /*fun removeOldItem(){
        val builder = AlertDialog.Builder()
        builder.setTitle("Item löschen")
        builder.setMessage("Möchten Sie das Item wirklich löschen?")
        builder.setPositiveButton("Löschen"){_, _ ->
            val itemName = items[position].title
            ItemAdapter.removeItem(position)
            Toast.makeText(
                binding.root.context,
                "$itemName gelöscht",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("Abbrechen", null)
        builder.show()
    }*/
}
