package handleLightsFeature.data.repository

import application.logger.LoggerWrapper
import handleLightsFeature.data.controller.LightsController
import handleLightsFeature.domain.model.LightStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import java.util.logging.Logger

internal class LightStatusRepositoryImplTest {


    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val lightsController = Mockito.mock(LightsController::class.java)
    private val repository = LightStatusRepositoryImpl(loggerWrapper, lightsController)

    @Test
    @DisplayName("The cache of the local storage should be valid")
    fun localCacheShouldBeValid() {
        val currentTime = System.currentTimeMillis()
        repository.cacheTimeStamp = currentTime-3000
        assertTrue(repository.isLocalCacheValid())
    }

    @Test
    @DisplayName("The cache of the local storage should be invalid")
    fun localCacheShouldBeInvalid() {
        val currentTime = System.currentTimeMillis()
        repository.cacheTimeStamp = currentTime-900001
        assertFalse(repository.isLocalCacheValid())
    }

    @Test
    @DisplayName("When the cache is not valid anymore, we ask for the real current status")
    fun askingTheCurrentStatusWhenCacheIsNotValid() {
        val currentTime = System.currentTimeMillis()
        repository.cacheTimeStamp = currentTime-900001
        repository.currentLightStatus = LightStatus.ON
        Mockito.`when`(lightsController.getCurrentLightStatus()).thenReturn(LightStatus.OFF)
        repository.updateLightStatus(LightStatus.OFF)
        Mockito.verify(lightsController, Mockito.times(1)).getCurrentLightStatus()
    }

    @Test
    @DisplayName("When the cache is still valid, we don't ask for the real current status")
    fun notAskingTheCurrentStatusWhenCacheIsValid() {
        val currentTime = System.currentTimeMillis()
        repository.cacheTimeStamp = currentTime-3000
        repository.currentLightStatus = LightStatus.OFF
        Mockito.`when`(lightsController.getCurrentLightStatus()).thenReturn(LightStatus.OFF)
        repository.updateLightStatus(LightStatus.OFF)
        Mockito.verify(lightsController, Mockito.times(0)).getCurrentLightStatus()
    }

    @Test
    @DisplayName("When the cache is not valid anymore, we update with the new status")
    fun updatingTheCacheWhenIsNotValid() {
        val currentTime = System.currentTimeMillis()
        repository.cacheTimeStamp = currentTime-900001
        repository.currentLightStatus = LightStatus.ON
        Mockito.`when`(lightsController.getCurrentLightStatus()).thenReturn(LightStatus.OFF)
        repository.updateLightStatus(LightStatus.OFF)
        assertEquals(repository.currentLightStatus, LightStatus.OFF)
    }

    @Test
    @DisplayName("When the cache is valid , we don't update with the new status")
    fun notUpdatingTheCacheWhenIsValid() {
        val currentTime = System.currentTimeMillis()
        repository.cacheTimeStamp = currentTime-3000
        repository.currentLightStatus = LightStatus.ON
        Mockito.`when`(lightsController.getCurrentLightStatus()).thenReturn(LightStatus.OFF)
        repository.updateLightStatus(LightStatus.ON)
        assertEquals(repository.currentLightStatus, LightStatus.ON)
    }

    @Test
    @DisplayName("When the new status is different than the current one, we update the status")
    fun updatingStatusWhenCurrentIsDifferent() {
        val currentTime = System.currentTimeMillis()
        repository.cacheTimeStamp = currentTime-3000
        repository.currentLightStatus = LightStatus.ON
        Mockito.`when`(lightsController.getCurrentLightStatus()).thenReturn(LightStatus.OFF)
        repository.updateLightStatus(LightStatus.OFF)
        Mockito.verify(lightsController, Mockito.times(1)).updateLightStatus(LightStatus.OFF)
    }

    @Test
    @DisplayName("When the new status is the same than the current one, we don't update the status")
    fun noUpdatingStatusWhenCurrentIsDifferent() {
        val currentTime = System.currentTimeMillis()
        repository.cacheTimeStamp = currentTime-3000
        repository.currentLightStatus = LightStatus.ON
        Mockito.`when`(lightsController.getCurrentLightStatus()).thenReturn(LightStatus.OFF)
        repository.updateLightStatus(LightStatus.ON)
        Mockito.verify(lightsController, Mockito.times(0)).updateLightStatus(LightStatus.ON)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}