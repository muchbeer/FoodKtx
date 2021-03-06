package raum.muchbeer.foodktx.repository.inMemory.byItem

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import raum.muchbeer.foodktx.api.RedditService
import raum.muchbeer.foodktx.model.RedditPost
import raum.muchbeer.foodktx.repository.NetworkState
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class ItemKeyedSubredditDataSource(
    private val redditService: RedditService,
    private val subredditName: String,
    private val retryExecutor: Executor
) : ItemKeyedDataSource<String, RedditPost>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

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


    override fun getKey(item: RedditPost): String = item.name

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<RedditPost>
    ) {
        val request = redditService.getTop(
            subreddit = subredditName,
            limit = params.requestedLoadSize
        )
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        try {
            val response = request.execute()
            val items = response.body()?.data?.children?.map { it.data } ?: emptyList()
            retry = null
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(items)
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }


    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {
        // set network value to loading.
        networkState.postValue(NetworkState.LOADING)
        // even though we are using async retrofit API here, we could also use sync
        // it is just different to show that the callback can be called async.
        redditService.getTopAfter(subreddit = subredditName,
            after = params.key,
            limit = params.requestedLoadSize).enqueue(
            object : retrofit2.Callback<RedditService.ListingResponse> {
                override fun onFailure(call: Call<RedditService.ListingResponse>, t: Throwable) {
                    // keep a lambda for future retry
                    retry = {
                        loadAfter(params, callback)
                                 }
                    // publish the error
                    networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
                }

                override fun onResponse(
                    call: Call<RedditService.ListingResponse>,
                    response: Response<RedditService.ListingResponse>
                ) {
                    if (response.isSuccessful) {
                        val items = response.body()?.data?.children?.map { it.data } ?: emptyList()
                        // clear retry since last request succeeded
                        retry = null
                        callback.onResult(items)
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

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {
        // ignored, since we only ever append to our initial load
    }

}