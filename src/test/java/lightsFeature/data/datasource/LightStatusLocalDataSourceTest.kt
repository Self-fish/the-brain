package lightsFeature.data.datasource

import lightsFeature.domain.model.LightStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito

internal class LightStatusLocalDataSourceTest {

    private val localDataSource = LightStatusLocalDataSource()

    @Test
    @DisplayName("The cache should be update with the current time")
    fun updateCache() {
        localDataSource.updateCache(LightStatus.ON)
        assertTrue(localDataSource.cacheTimeStamp <= System.currentTimeMillis())
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}