package handleLightsFeature.data.repository

import application.logger.LoggerWrapper
import handleLightsFeature.domain.model.LightStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import java.util.logging.Logger

internal class LightStatusRepositoryImplTest {


    //private val externalDataSource = Mockito.mock(LightStatusExternalDataSource::class.java)
    //private val localDataSource = Mockito.mock(LightStatusLocalDataSource::class.java)
    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    //private val repository = LightStatusRepositoryImpl(localDataSource, externalDataSource, loggerWrapper)

    /*@Test
    @DisplayName("The cache of the local storage should be valid")
    fun localCacheShouldBeValid() {
        val currentTime = System.currentTimeMillis()
        Mockito.`when`(localDataSource.cacheTimeStamp).thenReturn(currentTime-3000)
        assertTrue(repository.isLocalCacheValid())
    }

    @Test
    @DisplayName("The cache of the local storage should be invalid")
    fun localCacheShouldBeInvalid() {
        val currentTime = System.currentTimeMillis()
        Mockito.`when`(localDataSource.cacheTimeStamp).thenReturn(currentTime-900001)
        assertFalse(repository.isLocalCacheValid())
    }

    @Test
    @DisplayName("The cache is valid and should return the LightStatus")
    fun lightStatusFromLocalCache() {
        val currentTime = System.currentTimeMillis()
        Mockito.`when`(localDataSource.cacheTimeStamp).thenReturn(currentTime-3000)
        Mockito.`when`(localDataSource.currentLightStatus).thenReturn(LightStatus.ON)
        Mockito.`when`(externalDataSource.getLightStatus()).thenReturn(LightStatus.OFF)
        assertEquals(repository.getLightStatus(), LightStatus.ON)
        Mockito.verify(localDataSource, Mockito.times(0)).updateCache(LightStatus.OFF)

    }

    @Test
    @DisplayName("The cache is invalid so we should obtain the Lightstatus from external")
    fun lightStatusFromExternalDataSource() {
        val currentTime = System.currentTimeMillis()
        Mockito.`when`(localDataSource.cacheTimeStamp).thenReturn(currentTime-900001)
        Mockito.`when`(externalDataSource.getLightStatus()).thenReturn(LightStatus.ON)
        Mockito.`when`(localDataSource.currentLightStatus).thenReturn(LightStatus.ON)
        assertEquals(repository.getLightStatus(), LightStatus.ON)
        Mockito.verify(localDataSource, Mockito.times(1)).updateCache(LightStatus.ON)
    }*/

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}