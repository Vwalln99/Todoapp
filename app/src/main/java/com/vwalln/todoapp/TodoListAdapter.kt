package com.vwalln.todoapp

import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vwalln.todoapp.databinding.TodoListCardItemBinding

class TodoListAdapter (
    private val todoLists: MutableList<TodoList>,
    private val onItemClicked: (TodoList) -> Unit,
    private val onItemLongClicked: (TodoList, Int) -> Unit
    ) : RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {
    //Viewholder hält die referenzen zu den views der listenelemente
    inner class TodoListViewHolder(val binding: TodoListCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoList: TodoList) {
            //setzt den titel und die anzahl der items
            binding.todoListTitle.text = todoList.title
            binding.todoListItemCount.text = binding.root.context.resources.getQuantityString(
                R.plurals.todo_list_item_count,
                todoList.items.size,
                todoList.items.size
            )

            //clickevent wenn ein todolist-item angeklickt wird
            binding.root.setOnClickListener {
                onItemClicked(todoList)
            }
            binding.root.setOnLongClickListener{
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    onItemLongClicked(todoList, position)
                }
                true
            }
        }
    }

    //erstellt ein neues view für ein listenelement (viewholder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoListCardItemBinding.inflate(inflater, parent, false)
        return TodoListViewHolder(binding)
    }

    //bindet die daten an die views des viewholders
    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val todoList = todoLists[position]
        holder.bind(todoList)
    }

    //gibt die anzahl der todo listen zurück
    override fun getItemCount(): Int = todoLists.size

    //fügt neue liste hinzu
    fun addTodoList(todoList: TodoList) {
        todoLists.add(todoList)
        notifyItemInserted(todoLists.size - 1)
    }

    //löscht liste mit langem klick drauf
    fun removeTodoList(position: Int){
        todoLists.removeAt(position)
        notifyItemRemoved(position)
    }
}

