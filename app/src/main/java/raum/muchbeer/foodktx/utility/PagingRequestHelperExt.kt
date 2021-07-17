package raum.muchbeer.foodktx.utility



class PagingRequestHelperExt {

/*    private fun getErrorMessage(report: PagingRequestHelper.StatusReport): String {
        return PagingRequestHelperExt.RequestType.values().mapNotNull {
            report.getErrorFor(it)?.message
        }.first()
    }

    fun PagingRequestHelperExt.createStatusLiveData(): LiveData<NetworkState> {
        val liveData = MutableLiveData<NetworkState>()
        addListener { report ->
            when {
                report.hasRunning() -> liveData.postValue(NetworkState.LOADING)
                report.hasError() -> liveData.postValue(
                    NetworkState.error(getErrorMessage(report)))
                else -> liveData.postValue(NetworkState.LOADED)
            }
        }
        return liveData
    }*/

}