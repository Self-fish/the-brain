package showBoxHumidityFeature.domain.contract.controller

interface ScreenController {

    fun printBoxHumidity(temperature: Double): Boolean

}