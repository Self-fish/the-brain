package showBoxTemperatureFeature.domain.contract

interface ScreenController {
    fun printBoxTemperature(temperature: Double, date: String): Boolean
}