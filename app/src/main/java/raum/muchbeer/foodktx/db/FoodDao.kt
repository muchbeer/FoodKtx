package raum.muchbeer.foodktx.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.foodktx.model.Food

@Dao
interface FoodDao {

    @Query("SELECT * FROM food_tbl")
    fun getAllFoods(): Flow<List<Food>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodItems(foods: List<Food>)

    @Query("DELETE FROM food_tbl")
    suspend fun deleteAllFoods()

    @Query("SELECT * FROM food_tbl where title like  :foods")
    fun getSearchFoods(foods : String) : Flow<List<Food>>
}