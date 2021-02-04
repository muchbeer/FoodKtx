package raum.muchbeer.foodktx.api

import android.util.Log
import raum.muchbeer.foodktx.BuildConfig
import raum.muchbeer.foodktx.model.Food
import raum.muchbeer.foodktx.model.FoodModel
import retrofit2.Response


class ApiHelper( private val foodService: FoodService) {
    companion object {
        private const val API_KEY = "39be4261677f46598022e57a1ca64c4a"
    }
    suspend fun getFood() : Response<FoodModel> = foodService.getFood(BuildConfig.API_KEY, true)

/*{
        lateinit var listOfFood : List<Food>

        try {
            listOfFood = foodService.getFood(BuildConfig.API_KEY, true).body()!!.results
            Log.d("MovieNetwork", "retrieve from network: ${listOfFood}")
        } catch (exception: Exception) {

        }

    }*/
}