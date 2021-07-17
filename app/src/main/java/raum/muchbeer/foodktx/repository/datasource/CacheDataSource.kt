package raum.muchbeer.foodktx.repository.datasource

import kotlinx.coroutines.flow.Flow
import raum.muchbeer.foodktx.model.Food

interface CacheDataSource {

     fun getAllFoods() : Flow<List<Food>>

    suspend fun insertFoodItems(data : List<Food>)

    suspend fun deleteFoods()

    fun getSearchedFoods(title: String) : Flow<List<Food>>
}