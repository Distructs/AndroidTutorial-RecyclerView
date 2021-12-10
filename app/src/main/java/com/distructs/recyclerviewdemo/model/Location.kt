package com.distructs.recyclerviewdemo.model

import android.graphics.Bitmap

data class Location(
    val icon: Bitmap?,
    val title: String,
    val latitude: String,
    val longitude: String
)
