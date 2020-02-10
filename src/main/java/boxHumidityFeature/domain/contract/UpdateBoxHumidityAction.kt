package boxHumidityFeature.domain.contract

interface UpdateBoxHumidityAction {

    companion object {
        const val HUMIDITY_SCREEN_UPDATE = "H_U"
        const val CACHE_TTL = 5000
    }

    fun updateBoxHumidity()

}