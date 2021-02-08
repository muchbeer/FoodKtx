package raum.muchbeer.foodktx.api

import android.util.Log
import raum.muchbeer.foodktx.BuildConfig
import raum.muchbeer.foodktx.model.Food
import raum.muchbeer.foodktx.model.FoodModel
import retrofit2.Response


class ApiHelper( private val foodService: FoodService) {
    suspend fun getFood() : Response<FoodModel> = foodService.getFood(BuildConfig.API_KEY, true)
}