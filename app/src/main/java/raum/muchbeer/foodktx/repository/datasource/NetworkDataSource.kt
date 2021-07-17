package raum.muchbeer.foodktx.repository.datasource

import raum.muchbeer.foodktx.model.Food

interface NetworkDataSource {
    suspend fun retrieveFood() : List<Food>
}