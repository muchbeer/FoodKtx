package raum.muchbeer.foodktx.viemodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import raum.muchbeer.foodktx.model.Food
import raum.muchbeer.foodktx.model.FoodState
import raum.muchbeer.foodktx.repository.FoodRepository
import raum.muchbeer.foodktx.utility.Resource
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(private val repository: FoodRepository) : ViewModel() {

 val retrieveFoods = repository.getFoods().asLiveData()


/*
  private  val _query = MutableLiveData<String>()
    val query : LiveData<String>
        get() = _query

    val retrieveSearchFood = _query.asFlow()
        .debounce(300)
        .filter {
            it.trim().isEmpty().not()
        }.distinctUntilChanged()
        .flatMapLatest {
            repository.getFoods(it)
        }.asLiveData()
*/


}