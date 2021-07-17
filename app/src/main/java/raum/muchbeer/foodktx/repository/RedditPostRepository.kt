package raum.muchbeer.foodktx.repository

import raum.muchbeer.foodktx.model.RedditPost

/**
 * Common interface shared by the different repository implementations.
 * Note: this only exists for sample purposes - typically an app would implement a repo once, either
 * network+db, or network-only
 */
interface RedditPostRepository {
    fun postsOfSubreddit(subReddit: String, pageSize: Int): Listing<RedditPost>

    enum class Type {
        IN_MEMORY_BY_ITEM,
        IN_MEMORY_BY_PAGE,
        DB
    }
}