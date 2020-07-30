package collectHumidityAndBoxTempFeature.domain.contracts

interface HumidityAndTemperatureRepository {
    fun collect(): Boolean
    fun getCurrentTemperature(): Double
    fun getCurrentHumidity(): Double
}