package raum.muchbeer.foodktx.repository.inDb

import androidx.annotation.MainThread
import androidx.paging.PagedList
import raum.muchbeer.foodktx.api.RedditService
import raum.muchbeer.foodktx.model.RedditPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class SubredditBoundaryCallback(
    private val subredditName: String,
    private val webservice: RedditService,
    private val handleResponse: (String, RedditService.ListingResponse?) -> Unit,
    private val ioExecutor: Executor,
    private val networkPageSize: Int
) : PagedList.BoundaryCallback<RedditPost>(){

 //   val helper = PagingRequestHelper(ioExecutor)
   // val networkState = helper.createStatusLiveData()

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: RedditPost) {
       /* helper.runIfNotRunning(*//*PagingRequestHelper.RequestType.AFTER*//*) {
            webservice.getTopAfter(
                subreddit = subredditName,
                after = itemAtEnd.name,
                limit = networkPageSize)
             //   .enqueue(createWebserviceCallback(it))
        }*/
    }

    /**
     * every time it gets new items, boundary callback simply inserts them into the database and
     * paging library takes care of refreshing the list if necessary.
     */
    private fun insertItemsIntoDb(
        response: Response<RedditService.ListingResponse>,
        /*   it:PagingRequestHelper.Request.Callback*/) {
        ioExecutor.execute {
            handleResponse(subredditName, response.body())
        //    it.recordSuccess()
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: RedditPost) {
        // ignored, since we only ever append to what's in the DB
    }
    private fun createWebserviceCallback(/*it: PagingRequestHelper.Request.Callback*/)
            : Callback<RedditService.ListingResponse> {
        return object : Callback<RedditService.ListingResponse> {
            override fun onFailure(
                call: Call<RedditService.ListingResponse>,
                t: Throwable) {
           //     it.recordFailure(t)
            }

            override fun onResponse(
                call: Call<RedditService.ListingResponse>,
                response: Response<RedditService.ListingResponse>
            ) {
              //  insertItemsIntoDb(response, it)
            }
        }
    }
}