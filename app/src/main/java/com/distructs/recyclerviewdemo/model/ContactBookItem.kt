package com.distructs.recyclerviewdemo.model

enum class ItemType { HEADER, CARD }
sealed class ContactBookItem(val id: ItemType) {
    data class ContactCard(val firstName: String, val lastName: String, val phone: String) : ContactBookItem(ItemType.CARD)
    data class ContactHeader(val headerLetter: Char) : ContactBookItem(ItemType.HEADER)
}
