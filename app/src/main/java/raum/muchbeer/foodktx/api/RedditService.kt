package raum.muchbeer.foodktx.api

import raum.muchbeer.foodktx.model.RedditChildrenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditService {

    fun getTop(
        @Path("subreddit") subreddit: String,
        @Query("limit") limit: Int): Call<ListingResponse>

    // for after/before param, either get from RedditDataResponse.after/before,
    // or pass RedditNewsDataResponse.name (though this is technically incorrect)
    @GET("/r/{subreddit}/hot.json")
    fun getTopAfter(
        @Path("subreddit") subreddit: String,
        @Query("after") after: String,
        @Query("limit") limit: Int): Call<ListingResponse>

    @GET("/r/{subreddit}/hot.json")
    fun getTopBefore(
        @Path("subreddit") subreddit: String,
        @Query("before") before: String,
        @Query("limit") limit: Int): Call<ListingResponse>

    @GET("/r/{subreddit}/hot.json")
    suspend fun getTop2(
        @Path("subreddit") subreddit: String,
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): ListingResponse

    class ListingResponse(val data: ListingData)

    class ListingData(
        val children: List<RedditChildrenResponse>,
        val after: String?,
        val before: String?
    )


}