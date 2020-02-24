package handleWaterTempFeature.data.repository

import handleWaterTempFeature.data.datasource.WaterTempExternalDataSource
import handleWaterTempFeature.data.datasource.WaterTempLocalDataSource
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import kotlin.test.assertTrue

internal class TemperatureRepositoryImplTest {

    private val localDataSource = Mockito.mock(WaterTempLocalDataSource::class.java)
    private val externalDataSource = Mockito.mock(WaterTempExternalDataSource::class.java)
    private val repository = TemperatureRepositoryImpl(localDataSource, externalDataSource)

    @Test
    @DisplayName("If the last update was more than 1 minute ago, we should ask again to the external data source")
    fun askToExternalDataSourceIfTheLastUpdateWasMoreThan1MinuteAgo() {
        Mockito.`when`(localDataSource.lastUpdateTimeStamp).thenReturn(System.currentTimeMillis() - 80000)
        Mockito.`when`(externalDataSource.getWaterTemp()).thenReturn(25.0)
        repository.getCurrentTemperature()
        Mockito.verify(externalDataSource, Mockito.times(1)).getWaterTemp()
    }

    @Test
    @DisplayName("If the last update was more than 1 minute ago, we should update the local cache")
    fun updateTheLocalCacheIfTheLastUpdateWasMoreThan1MinuteAgo() {
        Mockito.`when`(localDataSource.lastUpdateTimeStamp).thenReturn(System.currentTimeMillis() - 80000)
        Mockito.`when`(externalDataSource.getWaterTemp()).thenReturn(25.0)
        repository.getCurrentTemperature()
        Mockito.verify(localDataSource, Mockito.times(1)).updateCache(25.0)
    }

    @Test
    @DisplayName("If the last update was less than 1 minute ago, we should not use the external data source")
    fun noExternalDataSourceIfTheLastUpdateWasMoreLess1MinuteAgo() {
        Mockito.`when`(localDataSource.lastUpdateTimeStamp).thenReturn(System.currentTimeMillis() - 1)
        repository.getCurrentTemperature()
        Mockito.verify(externalDataSource, Mockito.times(0)).getWaterTemp()
        Mockito.verify(localDataSource, Mockito.times(0)).updateCache(any(Double::class.java))
    }

    @Test
    @DisplayName("If the last update was less than 1 minute ago, we should return the local data source value")
    fun localDatSourceUSedIfTheLastUpdateWasMoreLess1MinuteAgo() {
        Mockito.`when`(localDataSource.lastUpdateTimeStamp).thenReturn(System.currentTimeMillis() - 1)
        Mockito.`when`(localDataSource.waterTemp).thenReturn(23.0)
        assertTrue(repository.getCurrentTemperature() == 23.0)

    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}