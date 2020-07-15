package handleLightsFeature.domain

import application.logger.LoggerWrapper
import handleLightsFeature.domain.contracts.repository.LightPreferencesRepository
import handleLightsFeature.domain.contracts.repository.LightStatusRepository
import handleLightsFeature.domain.model.LightPreferences
import handleLightsFeature.domain.model.LightStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.logging.Logger

internal class HandleLightsUseCaseTest {

    private val statusRepository = Mockito.mock(LightStatusRepository::class.java)
    private val preferencesRepository = Mockito.mock(LightPreferencesRepository::class.java)
    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val useCase = HandleLightsUseCase(statusRepository, preferencesRepository, loggerWrapper)

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
        useCase.handleLights()
        Mockito.verify(statusRepository, Mockito.times(1)).updateLightStatus(LightStatus.ON)
    }

    @Test
    @DisplayName("When is time to switch OFF lights the action is executed properly")
    fun actionIsExecutedWhenSwitchOFF() {
        Mockito.`when`(preferencesRepository.getLightsPreferences()).
                thenReturn(LightPreferences(
                        LocalDateTime.now().minusMinutes(10).format(DateTimeFormatter.ofPattern("HH:mm")),
                        LocalDateTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern("HH:mm"))))
        useCase.handleLights()
        Mockito.verify(statusRepository, Mockito.times(1)).updateLightStatus(LightStatus.OFF)
    }
    
    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }

}