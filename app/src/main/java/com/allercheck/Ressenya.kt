package com.allercheck

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ressenya(
    @SerializedName("id") val id: String,
    @SerializedName("restaurantId") val restaurantId: String,
    @SerializedName("restaurantName") val restaurantName: String,
    @SerializedName("stars") var stars: Int,
    @SerializedName("reviewText") var reviewText: String,
    @SerializedName("confirmed") var confirmed: Boolean
) : Parcelable
