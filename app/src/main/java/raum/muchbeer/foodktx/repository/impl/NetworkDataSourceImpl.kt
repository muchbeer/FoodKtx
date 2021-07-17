package raum.muchbeer.foodktx.repository.impl

import raum.muchbeer.foodktx.BuildConfig
import raum.muchbeer.foodktx.api.FoodService
import raum.muchbeer.foodktx.model.Food
import raum.muchbeer.foodktx.repository.datasource.NetworkDataSource
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(val dataService : FoodService) :
                          NetworkDataSource{

      override suspend fun retrieveFood(): List<Food> {
    return    dataService.getFood(BuildConfig.API_KEY, true ).body()!!.results    }
}