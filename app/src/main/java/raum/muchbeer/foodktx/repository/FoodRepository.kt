package raum.muchbeer.foodktx.repository

import raum.muchbeer.foodktx.api.FoodService
import raum.muchbeer.foodktx.model.FoodModel
import raum.muchbeer.foodktx.repository.datasource.CacheDataSource
import raum.muchbeer.foodktx.repository.datasource.NetworkDataSource
import raum.muchbeer.foodktx.repository.sync.networkBoundResource
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepository @Inject constructor(private val foodApi : NetworkDataSource,
                        private val foodDb : CacheDataSource) {

    fun getFoods() = networkBoundResource(
        query =   {
            foodDb.getAllFoods()
                  },
        fetch = {
            foodApi.retrieveFood()
        },
        saveFetchResult = {
            foodDb.deleteFoods()
            foodDb.insertFoodItems(it)
        }
    )

}