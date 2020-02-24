package showBoxTemperatureFeature.domain.contract

interface UpdateBoxTemperatureAction {

    companion object {
        const val BOX_TEMP_SCREEN_UPDATE = "T_U"
        const val CACHE_TTL = 5000
    }

    fun updateBoxTemperature()

}