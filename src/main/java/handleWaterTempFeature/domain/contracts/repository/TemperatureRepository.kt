package handleWaterTempFeature.domain.contracts.repository

interface TemperatureRepository {

    fun getCurrentTemperature(): Double

}