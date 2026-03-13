package com.allercheck

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val id: String,val name: String? = "",
    val rating: String? = "0.0",


    //@SerializedName porque sino no va, debido a que no coinciden los nombres y aunque lo ponga sino, no funciona, esta es la unica opcion que he encontrado para que funcione
    @SerializedName("verified") val isVerified: Boolean = false,
    @SerializedName("senseGluten") val senseGluten: Boolean = false,
    @SerializedName("senseLactosa") val senseLactosa: Boolean = false,
    @SerializedName("senseFruitsSecs") val senseFruitsSecs: Boolean = false,
    @SerializedName("senseMarisc") val senseMarisc: Boolean = false,
    @SerializedName("esVega") val esVega: Boolean = false,
    @SerializedName("esHalal") val esHalal: Boolean = false,
    @SerializedName("esKosher") val esKosher: Boolean = false,
    @SerializedName("teOu") val teOu: Boolean = false,
    @SerializedName("tipusCuina") val tipusCuina: String? = "",
    @SerializedName("rangPreu") val rangPreu: String? = "",
    @SerializedName("ubicacio") val ubicacio: String? = "",
    @SerializedName("telefon") val telefon: String? = "",
    @SerializedName("favorite") val isFavorite: Boolean = false
) : Parcelable