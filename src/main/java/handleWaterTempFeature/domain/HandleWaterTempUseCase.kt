package handleWaterTempFeature.domain

import handleWaterTempFeature.domain.contracts.repository.HeaterStatusRepository
import handleWaterTempFeature.domain.contracts.repository.TemperatureRepository
import handleWaterTempFeature.domain.contracts.repository.WaterTempPreferencesRepository
import handleWaterTempFeature.domain.model.HeaterStatus

class HandleWaterTempUseCase(private val temperatureRepository: TemperatureRepository,
                             private val temperaturePreferences: WaterTempPreferencesRepository,
                             private val heaterStatusRepository: HeaterStatusRepository) {


    fun handleWaterTemp() {
        val currentTemperature = temperatureRepository.getCurrentTemperature()
        val tempPreferences = temperaturePreferences.getWaterTempPreferences()
        val headerStatus = handleHeaterStatus(currentTemperature, tempPreferences)
        heaterStatusRepository.updateHeaterStatus(headerStatus)
    }

    fun handleHeaterStatus(currentTemperature: Double, tempPreferences: Double): HeaterStatus {
        return if(currentTemperature < tempPreferences) {
            HeaterStatus.ON
        } else {
            HeaterStatus.OFF
        }
    }






}