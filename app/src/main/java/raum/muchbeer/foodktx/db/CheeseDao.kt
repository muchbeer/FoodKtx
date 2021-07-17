package raum.muchbeer.foodktx.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import raum.muchbeer.foodktx.model.Cheese

@Dao
interface CheeseDao {
    /**
     * Room knows how to return a LivePagedListProvider, from which we can get a LiveData and serve
     * it back to UI via ViewModel.
     */
    @Query("SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC")
    fun allCheesesByName(): DataSource.Factory<Int, Cheese>

    @Insert
    fun insert(cheeses: List<Cheese>)

    @Insert
    fun insert(cheese: Cheese)

    @Delete
    fun delete(cheese: Cheese)
}