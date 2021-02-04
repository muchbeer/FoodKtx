package raum.muchbeer.foodktx.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import raum.muchbeer.foodktx.api.ApiHelper
import raum.muchbeer.foodktx.viemodel.FoodViewModel
import java.lang.IllegalArgumentException

class FoodViewModelFactory(val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if(modelClass.isAssignableFrom(FoodViewModel::class.java)) {
        return FoodViewModel(FoodRepository(apiHelper)) as T
      }
        throw IllegalArgumentException("Unknown Class name")
    }
}