package com.allercheck

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ItemService {
    // --- RESTAURANTS ---
    @GET("api/restaurant")
    suspend fun getRestaurants(
        @Query("senseGluten") senseGluten: Boolean? = null,
        @Query("senseLactosa") senseLactosa: Boolean? = null,
        @Query("senseFruitsSecs") senseFruitsSecs: Boolean? = null,
        @Query("senseMarisc") senseMarisc: Boolean? = null,
        @Query("esVega") esVega: Boolean? = null,
        @Query("esHalal") esHalal: Boolean? = null,
        @Query("esKosher") esKosher: Boolean? = null,
        @Query("teOu") teOu: Boolean? = null
    ): Response<List<Restaurant>>

    // --- FAVORITS ---
    @GET("api/favorites")
    suspend fun getFavorites(): Response<List<Restaurant>>

    @POST("api/favorites")
    suspend fun addFavorite(@Body body: Map<String, String>): Response<Void>

    @DELETE("api/favorites/{id}")
    suspend fun deleteFavorite(@Path("id") id: String): Response<Void>

    // --- RESSENYES ---
    @GET("api/reviews")
    suspend fun getAllReviews(): Response<List<Ressenya>>

    @POST("api/reviews")
    suspend fun createReview(@Body review: Ressenya): Response<Ressenya>

    @PUT("api/reviews/{id}")
    suspend fun updateReview(@Path("id") id: String, @Body review: Ressenya): Response<Ressenya>

    @DELETE("api/reviews/{id}")
    suspend fun deleteReview(@Path("id") id: String): Response<Void>
}

class ItemAPI {
    companion object {
        private var mItemAPI: ItemService? = null


        //URL SERVER
        private const val BASE_URL = "http://129.213.71.100:8081/"

        @Synchronized
        fun API(): ItemService {
            if (mItemAPI == null) {
                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()

                mItemAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .build()
                    .create(ItemService::class.java)
            }
            return mItemAPI!!
        }
    }
}