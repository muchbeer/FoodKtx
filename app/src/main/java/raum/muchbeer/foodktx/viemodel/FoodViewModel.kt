package raum.muchbeer.foodktx.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import raum.muchbeer.foodktx.model.Food
import raum.muchbeer.foodktx.repository.FoodRepository
import raum.muchbeer.foodktx.utility.Resource

class FoodViewModel(private val repository: FoodRepository) : ViewModel() {

    fun getAllFoods() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            lateinit var listOfFood : List<Food>
            listOfFood = repository.retrieveFood().body()!!.results
          //  emit(Resource.success(data = repository.retrieveFood()))
            emit(Resource.success(data = listOfFood))
        } catch (exception: Exception) {
            emit(Resource.error(data = null,message = exception.message ?: "Unknown error"))
        }
    }

}