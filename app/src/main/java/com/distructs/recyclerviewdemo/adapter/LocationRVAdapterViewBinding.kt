package com.distructs.recyclerviewdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.distructs.recyclerviewdemo.model.Location
import com.distructs.recyclerviewdemo.databinding.ItemLocationBinding

class LocationRVAdapterViewBinding(private val dataset: List<Location>) :
    RecyclerView.Adapter<LocationRVAdapterViewBinding.LocationVH>() {
    // When using ViewBinding, we need a Binding class for our ViewHolder
    // (this is how we ultimately reference views), we pass the 'root'
    // the the binding to RecyclerView.ViewHolder as this represents the root view.
    // We turn the 'binding' argument into a property (via val keyword)
    // so we can access it in the 'bind' method
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
        holder.bind(dataset[position])
    }

    override fun getItemCount() = dataset.size
}