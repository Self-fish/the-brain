package showBoxTemperatureFeature.domain.contract

interface UpdateBoxTemperatureAction {

    companion object {
        const val BOX_TEMP_SCREEN_UPDATE = "T_U"
        const val OK = "OK"
    }

    fun updateBoxTemperature() : Boolean

}