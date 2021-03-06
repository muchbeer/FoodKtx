package raum.muchbeer.foodktx.repository.inMemory.byPage

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import raum.muchbeer.foodktx.api.RedditService
import raum.muchbeer.foodktx.model.RedditPost
import raum.muchbeer.foodktx.repository.NetworkState
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class PagedKeySubredditDataSource(
    private val redditApi: RedditService,
    private val subredditName: String,
    private val retryExecutor: Executor
) : PageKeyedDataSource<String, RedditPost>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }


    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, RedditPost>
    ) {

        val request = redditApi.getTop(
            subreddit = subredditName,
            limit = params.requestedLoadSize
        )
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        try {
            val response = request.execute()
            val data = response.body()?.data
            val items = data?.children?.map { it.data } ?: emptyList()
            retry = null
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(items, data?.before, data?.after)
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, RedditPost>
    ) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        networkState.postValue(NetworkState.LOADING)
        redditApi.getTopAfter(subreddit = subredditName,
            after = params.key,
            limit = params.requestedLoadSize).enqueue(
            object : retrofit2.Callback<RedditService.ListingResponse> {
                override fun onFailure(call: Call<RedditService.ListingResponse>, t: Throwable) {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
                }

                override fun onResponse(
                    call: Call<RedditService.ListingResponse>,
                    response: Response<RedditService.ListingResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        val items = data?.children?.map { it.data } ?: emptyList()
                        retry = null
                        callback.onResult(items, data?.after)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(
                            NetworkState.error("error code: ${response.code()}"))
                    }
                }
            }
        )
    }
}