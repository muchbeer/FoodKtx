package raum.muchbeer.foodktx.model

sealed class FoodState<T>(
  val data : T? = null,
  val message : Throwable? = null
)  {

    class Success<T>(data: T) : FoodState<T>(data)
    class Loading<T>(data : T?=null) : FoodState<T>(data)
    class Error<T>(message: Throwable, data: T?=null) : FoodState<T>(data, message)
}