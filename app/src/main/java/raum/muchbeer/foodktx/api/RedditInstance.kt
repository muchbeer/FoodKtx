package raum.muchbeer.foodktx.api

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditInstance {

    companion object {
        private const val BASE_URL = "https://www.reddit.com/"
      //  fun create(): RedditApi = create(HttpUrl.parse(BASE_URL)!!)


        fun create(httpUrl: HttpUrl): RedditService {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditService::class.java)
        }
    }
}