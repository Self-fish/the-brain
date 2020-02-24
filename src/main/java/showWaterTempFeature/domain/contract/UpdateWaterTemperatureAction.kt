package showWaterTempFeature.domain.contract

interface UpdateWaterTemperatureAction {

    companion object {
        const val WATER_TEMP_SCREEN_UPDATE = "W_U"
        const val CACHE_TTL = 5000
    }

    fun updateWaterTemperature()

}