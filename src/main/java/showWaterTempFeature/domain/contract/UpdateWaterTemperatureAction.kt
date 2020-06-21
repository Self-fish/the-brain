package showWaterTempFeature.domain.contract

interface UpdateWaterTemperatureAction {

    companion object {
        const val WATER_TEMP_SCREEN_UPDATE = "W_U"
        const val OK = "OK"
    }

    fun updateWaterTemperature() : Boolean

}