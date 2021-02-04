package raum.muchbeer.foodktx.repository

import raum.muchbeer.foodktx.api.ApiHelper
import raum.muchbeer.foodktx.model.FoodModel
import retrofit2.Response

class FoodRepository(private val apiHelper: ApiHelper) {
    suspend fun retrieveFood() : Response<FoodModel> = apiHelper.getFood()
}