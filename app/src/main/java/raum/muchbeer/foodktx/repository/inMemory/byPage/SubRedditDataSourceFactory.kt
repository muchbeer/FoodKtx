package raum.muchbeer.foodktx.repository.inMemory.byPage

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import raum.muchbeer.foodktx.api.RedditService
import raum.muchbeer.foodktx.model.RedditPost
import java.util.concurrent.Executor

class SubRedditDataSourceFactory(
    private val redditApi: RedditService,
    private val subredditName: String,
    private val retryExecutor: Executor
) : DataSource.Factory<String, RedditPost>(){

    val sourceLiveData = MutableLiveData<PagedKeySubredditDataSource>()

    override fun create(): DataSource<String, RedditPost> {
        val source = PagedKeySubredditDataSource(redditApi, subredditName, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}