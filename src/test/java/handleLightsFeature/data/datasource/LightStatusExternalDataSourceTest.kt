package handleLightsFeature.data.datasource

import handleLightsFeature.data.action.LightActionImpl
import handleLightsFeature.domain.model.LightStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito

internal class LightStatusExternalDataSourceTest {

    private val action = Mockito.mock(LightActionImpl::class.java)
    private val dataSource = LightStatusExternalDataSource(action)

    @Test
    @DisplayName("When the action return ON, the external data source return LightStatus.ON")
    fun getLightStatusON() {
        Mockito.`when`(action.getLightStatus()).thenReturn("ON")
        assertTrue(dataSource.getLightStatus() == LightStatus.ON)
    }

    @Test
    @DisplayName("When the action return OFF, the external data source return LightStatus.ON")
    fun getLightStatusOFF() {
        Mockito.`when`(action.getLightStatus()).thenReturn("OFF")
        assertTrue(dataSource.getLightStatus() == LightStatus.OFF)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}