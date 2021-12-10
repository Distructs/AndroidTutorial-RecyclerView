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

// Similar implementation as [ContactListAdapterViewBinding] but adds an even listener
// for reacting to click events on items. See [ContactListAdapterViewBinding] before
// reading class.
// We implement a click listener through the lambda function
// we pass as an argument to the ContactListAdapterClickEvent instance.
// This is then used as an argument when we create the ViewHolder for ContactCard items.
// When a ContactCard (not ContactHeader) item is clicked, the item is passed back to the lambda
// function.
class ContactListAdapterClickEvent(private val onItemClicked: (ContactBookItem.ContactCard) -> Unit) :
    ListAdapter<ContactBookItem, RecyclerView.ViewHolder>(DiffCallback()) {
    companion object {
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
    class ContactCardVH(private val binding: ItemContactCardBinding, private val onClick: (ContactBookItem.ContactCard) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContactBookItem.ContactCard) {
            binding.root.setOnClickListener { onClick(item) }
            binding.firstNameTV.text = item.firstName
            binding.lastNameTV.text = item.lastName
            binding.phoneTV.text = item.phone
        }

        companion object {
            fun from(parent: ViewGroup, onItemClicked: (ContactBookItem.ContactCard) -> Unit): ContactCardVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemContactCardBinding.inflate(layoutInflater, parent, false)
                return ContactCardVH(binding, onItemClicked)
            }
        }
    }

    // Here, we choose which ViewHolder to create based on the viewType property
    // this property is set by the getItemViewType() method that you must override.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.HEADER.ordinal -> ContactHeaderVH.from(parent)
            ItemType.CARD.ordinal -> ContactCardVH.from(parent, onItemClicked)
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