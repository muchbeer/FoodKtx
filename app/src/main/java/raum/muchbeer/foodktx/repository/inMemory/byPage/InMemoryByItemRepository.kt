package raum.muchbeer.foodktx.repository.inMemory.byPage

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import raum.muchbeer.foodktx.api.RedditService
import raum.muchbeer.foodktx.model.RedditPost
import raum.muchbeer.foodktx.repository.Listing
import raum.muchbeer.foodktx.repository.RedditPostRepository
import java.util.concurrent.Executor

class InMemoryByItemRepository(
    private val redditApi: RedditService,
    private val networkExecutor: Executor
) : RedditPostRepository{


    @MainThread
    override fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost> {

        val sourceFactory = SubRedditDataSourceFactory(redditApi, subReddit, networkExecutor)

        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = sourceFactory.toLiveData(
            pageSize = pageSize,
            // provide custom executor for network requests, otherwise it will default to
            // Arch Components' IO pool which is also used for disk access
            fetchExecutor = networkExecutor)

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
    }
}