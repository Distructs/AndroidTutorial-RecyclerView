package com.distructs.recyclerviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.distructs.recyclerviewdemo.adapter.ContactListAdapterClickEvent
import com.distructs.recyclerviewdemo.adapter.ContactListAdapterViewBinding
import com.distructs.recyclerviewdemo.adapter.LocationRVAdapterTraditional
import com.distructs.recyclerviewdemo.adapter.LocationListAdapterTraditional
import com.distructs.recyclerviewdemo.model.ContactBookItem
import com.distructs.recyclerviewdemo.model.Location

class MainActivity : AppCompatActivity() {
    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val locations = listOf(
        Location(
            null,
        "Mountains",
        "34.3",
        "28.2"),
        Location(
            null,
            "Beach",
            "34.3",
            "28.2"),
        Location(
            null,
            "Whoville",
            "34.3",
            "28.2"),
        Location(
            null,
            "A Park",
            "34.3",
            "28.2")
    )

    private val contacts = listOf(
        ContactBookItem.ContactCard("Aerial", "Skylark", "562-333-2222"),
        ContactBookItem.ContactCard("Alaska", "Herring", "310-222-3344"),
        ContactBookItem.ContactCard("Bodog", "Buckle", "310-222-3244"),
        ContactBookItem.ContactCard("Boron", "Skrapsi", "333-222-1122"),
        ContactBookItem.ContactCard("Heren", "Kelly", "562-152-3344"),
        ContactBookItem.ContactCard("Zorksaborg", "Juniper", "789-222-3344")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buildRVWithAdapterTraditional()

        // try commenting out the line above and add one of the other "buildRV..." methods
    }

    // This is used to build a recyclerview when our
    // adapter extends RecyclerView.Adapter.
    // Useful for simple data that will not change often.
    private fun buildRVWithAdapterTraditional() {
        val adapter = LocationRVAdapterTraditional(locations)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // This is used to build a recyclerview when our
    // adapter extends ListAdapter.
    // Useful when our data may change often.
    private fun buildRVWithListAdapterTraditional() {
        val adapter = LocationListAdapterTraditional()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        // This is how we add data to our adapter, which our recyclerview
        // will then display. This is useful if we have data that may constantly
        // change in our recyclerview as this provides a more efficient method.
        adapter.submitList(locations)
    }

    private fun buildRVWithMultipleViewTypes() {
        val adapter = ContactListAdapterViewBinding()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.submitList(buildAlphabeticalContactCards())
    }

    private fun buildRVWithMultipleViewTypesAndClickEvent() {
        val adapter = ContactListAdapterClickEvent() { contactCard ->
            Log.d("logz", "Item clicked $contactCard")
            // This gets called when a ContactCard item is clicked (not header)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.submitList(buildAlphabeticalContactCards())
    }

    private fun buildAlphabeticalContactCards(): List<ContactBookItem> {
        val contactBookItems = mutableListOf<ContactBookItem>()
        val firstNameCharacters = contacts.map { it.firstName.first().uppercaseChar() }.toSortedSet()
        firstNameCharacters.forEach { character ->
            // Add the current character as a leader header (A, B, C, etc)
            contactBookItems.add(ContactBookItem.ContactHeader(character))

            // Add any items from the list of contacts that have the same character
            // for the first name ('A' would include "Aerial" and "Alaska")
            contactBookItems.addAll(contacts.filter { it.firstName.first().uppercaseChar() == character })
        }
        return contactBookItems
    }
}