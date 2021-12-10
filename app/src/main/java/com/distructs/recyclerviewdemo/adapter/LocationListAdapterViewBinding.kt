package com.distructs.recyclerviewdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.distructs.recyclerviewdemo.model.Location
import com.distructs.recyclerviewdemo.databinding.ItemLocationBinding

class LocationListAdapterViewBinding :
    ListAdapter<Location, LocationListAdapterViewBinding.LocationVH>(DiffCallback()) {
    companion object {
        class DiffCallback : DiffUtil.ItemCallback<Location>() {
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
                // We would normally select a unique identifier such as an ID,
                // This determines is two items are the same
                val latitudesSame = oldItem.latitude == newItem.latitude
                val longitudesSame = oldItem.longitude == newItem.longitude
                return latitudesSame && longitudesSame
            }

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
                // Since we use data classes, we automatically generate equals methods for
                // all properties and this will compare if two items have the same contents
                // (e.g., location title, latitude, longitude, etc)
                return oldItem == newItem
            }

        }
    }

    class LocationVH(private val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        // No need to create references to each view, we have them via binding

        // Since we hold a reference to the views in each item, we should
        // be responsible for updating the data within these views.
        fun bind(item: Location) {
            // We use the binding class to reference all our views.
            item.icon?.let { binding.icon.setImageBitmap(it) }
            binding.titleTV.text = item.title
            binding.latTV.text = "Lat: ${item.latitude}"
            binding.lonTV.text = "Lon: ${item.longitude}"
        }

        companion object {
            // Used to create an instance of LocationVH.
            // This is optional, you could move the code within this method
            // into 'onCreateViewHolder', but with clean code practices,
            // it's actually the responsibility of the LocationVH to create
            // an instance of itself, so this is more proper.
            fun from(parent: ViewGroup): LocationVH {

                // Get an instance of the LayoutInflater, this is used to inflate views in Android
                val layoutInflater = LayoutInflater.from(parent.context)

                // We create an instance of the binding class using the layout inflater
                val binding = ItemLocationBinding.inflate(layoutInflater, parent, false)

                return LocationVH(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LocationVH.from(parent)

    override fun onBindViewHolder(holder: LocationVH, position: Int) {
        // getItem is provided by the ListAdapter and gets an item
        // at the specified position for the current dataset
        holder.bind(getItem(position))
    }
}