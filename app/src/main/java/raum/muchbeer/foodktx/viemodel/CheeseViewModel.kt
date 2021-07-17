package raum.muchbeer.foodktx.viemodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import raum.muchbeer.foodktx.db.CheeseDatabase
import raum.muchbeer.foodktx.model.Cheese

class CheeseViewModel(app: Application) : AndroidViewModel(app) {

    val dao = CheeseDatabase.getInstance(app).cheeseDao()

    val retrieveCheese: LiveData<PagedList<Cheese>> =
        dao.allCheesesByName().toLiveData(Config(
            pageSize = 60,
            enablePlaceholders = true,
            maxSize = 200
        ))


    val allCheeses = dao.allCheesesByName().toLiveData(
        Config(
            pageSize = 60,
            enablePlaceholders = true,
            maxSize = 200
        )
    )

    fun insert(text: CharSequence) =viewModelScope.launch(Dispatchers.IO) {
       dao.insert(Cheese(id = 0, name = text.toString()))
   } /*withContext(Dispatchers.IO) {

    }*/

    fun remove(cheese: Cheese) = viewModelScope.launch(Dispatchers.IO) {
        dao.delete(cheese)
    }
}