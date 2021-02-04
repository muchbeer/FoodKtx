package raum.muchbeer.foodktx.api

import raum.muchbeer.foodktx.model.Food
import raum.muchbeer.foodktx.model.FoodModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FoodService {

    @Headers("Content-Type: application/json")
    @GET("recipes/complexSearch")
    suspend fun getFood(@Query("apiKey") apiKey : String,
                        @Query("includeNutritition") incldeNutrn : Boolean) : Response<FoodModel>

}