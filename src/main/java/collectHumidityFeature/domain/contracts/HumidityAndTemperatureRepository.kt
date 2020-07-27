package collectHumidityFeature.domain.contracts

interface HumidityAndTemperatureRepository {

    fun collect()
    fun getCurrentTemperature(): Double
    fun getCurrentHumidity(): Double

}