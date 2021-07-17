package raum.muchbeer.foodktx.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class RedditDb : RoomDatabase() {

    abstract fun redditDao() : RedditDao

    companion object {
        private var instance : RedditDb? = null

        @Synchronized
        fun redditInstance(context: Context) : RoomDatabase {
            if (instance== null) {
                return Room.databaseBuilder(context,
                RedditDb::class.java, "RedditDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance!!
        }
    }
}