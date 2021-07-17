package raum.muchbeer.foodktx.repository.impl

import kotlinx.coroutines.flow.Flow
import raum.muchbeer.foodktx.db.FoodDao
import raum.muchbeer.foodktx.model.Food
import raum.muchbeer.foodktx.repository.datasource.CacheDataSource

class CacheImpl(val foodDao: FoodDao) : CacheDataSource {
    override fun getAllFoods(): Flow<List<Food>> {
       return foodDao.getAllFoods()   }

    override suspend fun insertFoodItems(data: List<Food>) {
     foodDao.insertFoodItems(data)    }

    override suspend fun deleteFoods() {
     foodDao.deleteAllFoods()    }

    override fun getSearchedFoods(title: String): Flow<List<Food>> {
     return foodDao.getSearchFoods(title)    }
}