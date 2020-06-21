package showBoxHumidityFeature.domain.contract

interface UpdateBoxHumidityAction {

    companion object {
        const val HUMIDITY_SCREEN_UPDATE = "H_U"
        const val OK = "OK"
    }

    fun updateBoxHumidity() : Boolean

}