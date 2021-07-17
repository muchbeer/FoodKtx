package raum.muchbeer.foodktx.repository.sync

import kotlinx.coroutines.flow.*
import raum.muchbeer.foodktx.model.FoodState

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(FoodState.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { FoodState.Success(it) }
        } catch (throwable: Throwable) {
            query().map { FoodState.Error(throwable, it) }
        }
    } else {
        query().map { FoodState.Success(it) }
    }

    emitAll(flow)
}