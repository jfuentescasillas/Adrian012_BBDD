package com.example.adrian012_bbdd.presentation.fragment.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.adrian012_bbdd.databinding.ItemNoteBinding
import com.example.adrian012_bbdd.domain.model.NoteDomainModel


class NotesListAdapter(private var dataSet: List<NoteDomainModel>, private val callbacks: MyClicksListener): RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        return ViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup,false)
        )
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]

        viewHolder.binding.apply {
            itemNoteTextViewTitle.text = item.title
            itemNoteTextViewBody.text = item.body
            itemNoteButtonEdit.setOnClickListener {
                callbacks.onEditButtonClicked(item)
            }
            itemNoteButtonDelete.setOnClickListener{
                callbacks.onDeleteButtonClicked(item)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


    fun updateList(newList: List<NoteDomainModel>) {
        dataSet = newList
        notifyDataSetChanged()
    }


    interface MyClicksListener {
        fun onEditButtonClicked(item: NoteDomainModel)
        fun onDeleteButtonClicked(item: NoteDomainModel)
    }
}