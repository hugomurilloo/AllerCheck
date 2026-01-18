package com.allercheck

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize //ENVIABLE
data class Restaurant(
    val id: String,
    val name: String,
    val rating: String,
    val isVerified: Boolean,
    val senseGluten: Boolean,
    val senseLactosa: Boolean,
    val senseFruitsSecs: Boolean,
    val senseMarisc: Boolean,
    val esVega: Boolean,
    val esHalal: Boolean,
    val esKosher: Boolean,
    val teOu: Boolean,

    val tipusCuina: String,
    val rangPreu: String,
    val ubicacio: String,
    val telefon: String
) : Parcelable

