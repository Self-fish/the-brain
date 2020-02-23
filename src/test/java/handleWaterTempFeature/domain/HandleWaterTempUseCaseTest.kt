package handleWaterTempFeature.domain

import handleWaterTempFeature.domain.contracts.repository.HeaterStatusRepository
import handleWaterTempFeature.domain.contracts.repository.TemperatureRepository
import handleWaterTempFeature.domain.contracts.repository.WaterTempPreferencesRepository
import handleWaterTempFeature.domain.model.HeaterStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito

internal class HandleWaterTempUseCaseTest {

    private val waterTempRepository = Mockito.mock(TemperatureRepository::class.java)
    private val waterTempPreferences = Mockito.mock(WaterTempPreferencesRepository::class.java)
    private val heaterStatusRepository = Mockito.mock(HeaterStatusRepository::class.java)
    private val useCase = HandleWaterTempUseCase(waterTempRepository, waterTempPreferences, heaterStatusRepository)

    @Test
    @DisplayName("If current temp is less than the desired one, the heater should ON")
    fun turnOnHeaterIfWaterTempLessThanDesired() {
        Mockito.`when`(waterTempRepository.getCurrentTemperature()).thenReturn(25.0)
        Mockito.`when`(waterTempPreferences.getWaterTempPreferences()).thenReturn(26.0)
        useCase.handleWaterTemp()
        Mockito.verify(heaterStatusRepository, Mockito.times(1)).updateHeaterStatus(HeaterStatus.ON)
    }

    @Test
    @DisplayName("If current temp is more than the desired one, the heater should OFF")
    fun turnOFFHeaterIfWaterTempMoreThanDesired() {
        Mockito.`when`(waterTempRepository.getCurrentTemperature()).thenReturn(26.0)
        Mockito.`when`(waterTempPreferences.getWaterTempPreferences()).thenReturn(25.0)
        useCase.handleWaterTemp()
        Mockito.verify(heaterStatusRepository, Mockito.times(1)).updateHeaterStatus(HeaterStatus.OFF)
    }

    @Test
    @DisplayName("If current temp is equal than the desired one, the heater should ON")
    fun turnONHeaterIfWaterTempEqualThanDesired() {
        Mockito.`when`(waterTempRepository.getCurrentTemperature()).thenReturn(25.0)
        Mockito.`when`(waterTempPreferences.getWaterTempPreferences()).thenReturn(25.0)
        useCase.handleWaterTemp()
        Mockito.verify(heaterStatusRepository, Mockito.times(1)).updateHeaterStatus(HeaterStatus.ON)
    }
}