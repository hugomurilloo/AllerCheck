package com.allercheck

class ItemAPI {
   /* companion object {
        private var mItemAPI: ItemService? = null

        @Synchronized
        fun API(): ItemService {
            if (mItemAPI == null) {

                val gsondateformat = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()

                mItemAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsondateformat))
                    .baseUrl("MI IP")
                    .build()
                    .create(ItemService::class.java)
            }
            return mItemAPI!!
        }
    }*/
}