package com.distructs.recyclerviewdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.distructs.recyclerviewdemo.databinding.ItemContactCardBinding
import com.distructs.recyclerviewdemo.databinding.ItemContactHeaderBinding
import com.distructs.recyclerviewdemo.model.ContactBookItem
import com.distructs.recyclerviewdemo.model.ItemType

// You'll notice this implementation has the second generic argument of ListAdapter
// as any instance of RecyclerView.ViewHolder, so any class that subclasses it will work.
// We create a ViewHolder for each Type of view we will display. In our case we have "Header"
// items that contain the letter for a list of contact "Cards".
class ContactListAdapterViewBinding :
    ListAdapter<ContactBookItem, RecyclerView.ViewHolder>(DiffCallback()){
    companion object {

        // We create a generic DiffCallback that can accept any subclass of ContactBookItem
        // We compare ContactCard items by their phone number, but compare ContactHeader
        // items by their letter
        class DiffCallback : DiffUtil.ItemCallback<ContactBookItem>() {
            override fun areItemsTheSame(
                oldItem: ContactBookItem,
                newItem: ContactBookItem
            ): Boolean {
                return when {
                    oldItem is ContactBookItem.ContactCard && newItem is ContactBookItem.ContactCard -> {
                        oldItem.phone == newItem.phone
                    }
                    oldItem is ContactBookItem.ContactHeader && newItem is ContactBookItem.ContactHeader -> {
                        oldItem.headerLetter == newItem.headerLetter
                    }
                    else -> throw IllegalArgumentException("Illegal Type Used.")
                }
            }

            override fun areContentsTheSame(
                oldItem: ContactBookItem,
                newItem: ContactBookItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    // This ViewHolder is only responsible for inflating the Contact Header Item
    class ContactHeaderVH(private val binding: ItemContactHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactBookItem.ContactHeader) {
            binding.textView.text = item.headerLetter.toString()
        }

        companion object {
            fun from(parent: ViewGroup): ContactHeaderVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemContactHeaderBinding.inflate(layoutInflater, parent, false)
                return ContactHeaderVH(binding)
            }
        }
    }

    // This ViewHolder is only responsible for inflating the Contact Card Item
    class ContactCardVH(private val binding: ItemContactCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactBookItem.ContactCard) {
            binding.firstNameTV.text = item.firstName
            binding.lastNameTV.text = item.lastName
            binding.phoneTV.text = item.phone
        }

        companion object {
            fun from(parent: ViewGroup): ContactCardVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemContactCardBinding.inflate(layoutInflater, parent, false)
                return ContactCardVH(binding)
            }
        }
    }

    // Here, we choose which ViewHolder to create based on the viewType property
    // this property is set by the getItemViewType() method that you must override.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.HEADER.ordinal -> ContactHeaderVH.from(parent)
            ItemType.CARD.ordinal -> ContactCardVH.from(parent)
            else -> throw IllegalArgumentException("Illegal class type used.")
        }
    }

    // We have to do some casting here to bind the correct data to the correct ViewHolder
    // depending on what view we need to inflate
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContactHeaderVH -> holder.bind(getItem(position) as ContactBookItem.ContactHeader)
            is ContactCardVH -> holder.bind(getItem(position) as ContactBookItem.ContactCard)
            else -> throw IllegalArgumentException("Illegal class type used.")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).id.ordinal
    }
}