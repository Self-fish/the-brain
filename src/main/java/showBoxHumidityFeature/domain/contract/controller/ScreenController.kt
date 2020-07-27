package showBoxHumidityFeature.domain.contract.controller

interface ScreenController {

    fun printBoxHumidity(humidity: Double): Boolean

}