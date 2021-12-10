package com.distructs.recyclerviewdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.distructs.recyclerviewdemo.model.Location
import com.distructs.recyclerviewdemo.R

/**
 * This adapter uses the "traditional" method for inflating the parent and referencing other views within the layout.
 */

class LocationRVAdapterTraditional(private val dataset: List<Location>) :
    RecyclerView.Adapter<LocationRVAdapterTraditional.LocationVH>() {

    class LocationVH(view: View) : RecyclerView.ViewHolder(view) {
        // Make sure you reference the correct ID and type for
        // each view or your app will crash! (this is one reason
        // why we prefer view binding!)
        private val icon: ImageView = view.findViewById(R.id.icon)
        private val title: TextView = view.findViewById(R.id.titleTV)
        private val latitude: TextView = view.findViewById(R.id.latTV)
        private val longitude: TextView = view.findViewById(R.id.lonTV)

        // Since we hold a reference to the views in each item, we should
        // be responsible for updating the data within these views.
        fun bind(item: Location) {
            item.icon?.let { icon.setImageBitmap(it) }
            title.text = item.title
            latitude.text = "Lat: ${item.latitude}"
            longitude.text = "Lon: ${item.longitude}"
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

                // We use the instance of the LayoutInflater to inflate our view.
                // It creates the views according to the layout file we passed in,
                // but the data is still the same as it appears
                // in the layout file (not updated with our data)
                val view = layoutInflater.inflate(R.layout.item_location, parent, false)
                return LocationVH(view)
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