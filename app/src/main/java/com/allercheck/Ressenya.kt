package com.allercheck

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ressenya(
    val id: String,
    val restaurantId: String,
    val restaurantName: String,
    var stars: Int,
    var reviewText: String,
    var confirmed: Boolean
) : Parcelable
