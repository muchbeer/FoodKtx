package raum.muchbeer.foodktx.repository.inMemory.byItem

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import raum.muchbeer.foodktx.api.RedditService
import raum.muchbeer.foodktx.model.RedditPost
import raum.muchbeer.foodktx.repository.inMemory.byItem.ItemKeyedSubredditDataSource
import java.util.concurrent.Executor


class SubRedditDataSourceFactory(
    private val redditApi: RedditService,
    private val subredditName: String,
    private val retryExecutor: Executor
) : DataSource.Factory<String, RedditPost>(){

    val sourceLiveData = MutableLiveData<ItemKeyedSubredditDataSource>()
    override fun create(): DataSource<String, RedditPost> {
        val source = ItemKeyedSubredditDataSource(redditApi, subredditName, retryExecutor)

        sourceLiveData.postValue(source)
        return source
    }

}