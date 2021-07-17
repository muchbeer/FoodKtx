package raum.muchbeer.foodktx.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import raum.muchbeer.foodktx.model.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class FoodDb() : RoomDatabase() {

    abstract fun foodDao(): FoodDao


    companion object {

        private var instance: FoodDb? = null

        @Synchronized
        fun getFoodInstance(context: Context): FoodDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDb::class.java, "FoodDatabase"
                ).build()
            }
            return instance!!
        }
    }
}