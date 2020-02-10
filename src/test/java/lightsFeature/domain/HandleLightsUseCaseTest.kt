package lightsFeature.domain

import lightsFeature.domain.contracts.action.LightAction
import lightsFeature.domain.contracts.repository.LightPreferencesRepository
import lightsFeature.domain.contracts.repository.LightStatusRepository
import lightsFeature.domain.model.LightPreferences
import lightsFeature.domain.model.LightStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class HandleLightsUseCaseTest {

    private val statusRepository = Mockito.mock(LightStatusRepository::class.java)
    private val preferencesRepository = Mockito.mock(LightPreferencesRepository::class.java)
    private val action = Mockito.mock(LightAction::class.java)
    private val useCase = HandleLightsUseCase(statusRepository, preferencesRepository, action)

    @Test
    @DisplayName("When current time is less than starting time we should switch off lights")
    fun currentTimeLessThanStartingTime() {
        val currentTime = "07:00"
        val currentPreferences = LightPreferences("08:00", "14:00")
        assertFalse(useCase.shouldTurnOnLights(currentTime, currentPreferences))
    }

    @Test
    @DisplayName("When current time is more than finish time we should switch off lights")
    fun currentTimeMoreThanFinishTime() {
        val currentTime = "05:00"
        val currentPreferences = LightPreferences("08:00", "14:00")
        assertFalse(useCase.shouldTurnOnLights(currentTime, currentPreferences))
    }

    @Test
    @DisplayName("When current time is the same than starting time we should switch on lights")
    fun currentTimeEqualThanStartingTime() {
        val currentTime = "08:00"
        val currentPreferences = LightPreferences("08:00", "14:00")
        assertTrue(useCase.shouldTurnOnLights(currentTime, currentPreferences))
    }

    @Test
    @DisplayName("When current time is the same than finish time we should switch off lights")
    fun currentTimeEqualThanFinishTime() {
        val currentTime = "14:00"
        val currentPreferences = LightPreferences("08:00", "14:00")
        assertFalse(useCase.shouldTurnOnLights(currentTime, currentPreferences))
    }

    @Test
    @DisplayName("When current time is between staring and finishing time we should switch on lights")
    fun currentTimeBetweenStartAndFinishTime() {
        val currentTime = "10:00"
        val currentPreferences = LightPreferences("08:00", "14:00")
        assertTrue(useCase.shouldTurnOnLights(currentTime, currentPreferences))
    }

    @Test
    @DisplayName("When is time to switch ON lights the action is executed properly")
    fun actionIsExecutedWhenSwitchON() {
        Mockito.`when`(preferencesRepository.getLightsPreferences()).
                thenReturn(LightPreferences(
                        LocalDateTime.now().minusMinutes(1).format(DateTimeFormatter.ofPattern("HH:mm")),
                        LocalDateTime.now().plusMinutes(1).format(DateTimeFormatter.ofPattern("HH:mm"))))
        Mockito.`when`(statusRepository.getLightStatus()).thenReturn(LightStatus.ON)
        useCase.handleLights()
        Mockito.verify(action, Mockito.times(1)).turnOnLights(LightStatus.ON)
    }

    @Test
    @DisplayName("When is time to switch OFF lights the action is executed properly")
    fun actionIsExecutedWhenSwitchOFF() {
        Mockito.`when`(preferencesRepository.getLightsPreferences()).
                thenReturn(LightPreferences(
                        LocalDateTime.now().minusMinutes(10).format(DateTimeFormatter.ofPattern("HH:mm")),
                        LocalDateTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern("HH:mm"))))
        Mockito.`when`(statusRepository.getLightStatus()).thenReturn(LightStatus.ON)
        useCase.handleLights()
        Mockito.verify(action, Mockito.times(1)).turnOffLights(LightStatus.ON)
    }

    @Test
    @DisplayName("When Switch ON action is executed, the cache is updated")
    fun cacheIsUpdatedWhenSwitchON() {
        Mockito.`when`(preferencesRepository.getLightsPreferences()).
                thenReturn(LightPreferences(
                        LocalDateTime.now().minusMinutes(1).format(DateTimeFormatter.ofPattern("HH:mm")),
                        LocalDateTime.now().plusMinutes(1).format(DateTimeFormatter.ofPattern("HH:mm"))))
        Mockito.`when`(statusRepository.getLightStatus()).thenReturn(LightStatus.ON)
        Mockito.`when`(action.turnOnLights(LightStatus.ON)).thenReturn(true)
        useCase.handleLights()
        Mockito.verify(statusRepository, Mockito.times(1)).updateLightStatus(LightStatus.ON)
    }

    @Test
    @DisplayName("When Switch ON action is not properly executed, the cache is not updated")
    fun cacheIsNotUpdatedWhenSwitchONNotExecuted() {
        Mockito.`when`(preferencesRepository.getLightsPreferences()).
                thenReturn(LightPreferences(
                        LocalDateTime.now().minusMinutes(1).format(DateTimeFormatter.ofPattern("HH:mm")),
                        LocalDateTime.now().plusMinutes(1).format(DateTimeFormatter.ofPattern("HH:mm"))))
        Mockito.`when`(statusRepository.getLightStatus()).thenReturn(LightStatus.ON)
        Mockito.`when`(action.turnOnLights(LightStatus.ON)).thenReturn(false)
        useCase.handleLights()
        Mockito.verify(statusRepository, Mockito.times(0)).updateLightStatus(LightStatus.ON)
    }

    @Test
    @DisplayName("When Switch OFF action is executed, the cache is updated")
    fun cacheIsUpdatedWhenSwitchOFF() {
        Mockito.`when`(preferencesRepository.getLightsPreferences()).
                thenReturn(LightPreferences(
                        LocalDateTime.now().minusMinutes(10).format(DateTimeFormatter.ofPattern("HH:mm")),
                        LocalDateTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern("HH:mm"))))
        Mockito.`when`(statusRepository.getLightStatus()).thenReturn(LightStatus.ON)
        Mockito.`when`(action.turnOffLights(LightStatus.ON)).thenReturn(true)
        useCase.handleLights()
        Mockito.verify(statusRepository, Mockito.times(1)).updateLightStatus(LightStatus.OFF)
    }

    @Test
    @DisplayName("When Switch OFF action is not properly executed, the cache is not updated")
    fun cacheIsNotUpdatedWhenSwitchOFFNotExecuted() {
        Mockito.`when`(preferencesRepository.getLightsPreferences()).
                thenReturn(LightPreferences(
                        LocalDateTime.now().minusMinutes(10).format(DateTimeFormatter.ofPattern("HH:mm")),
                        LocalDateTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern("HH:mm"))))
        Mockito.`when`(statusRepository.getLightStatus()).thenReturn(LightStatus.ON)
        Mockito.`when`(action.turnOffLights(LightStatus.ON)).thenReturn(false)
        useCase.handleLights()
        Mockito.verify(statusRepository, Mockito.times(0)).updateLightStatus(LightStatus.OFF)
    }


    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }

}