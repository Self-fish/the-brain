package handleWaterTempFeature.data.datasource

import handleWaterTempFeature.data.action.WaterTempAction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import kotlin.test.assertTrue

internal class WaterTempExternalDataSourceTest {

    private val action = Mockito.mock(WaterTempAction::class.java)
    private val dataSource = WaterTempExternalDataSource(action)

    @Test
    @DisplayName("When asking the temp to the external data source, then the action is executed")
    fun callActionWhenRequestTheTempToTheExternalDataSource() {
        Mockito.`when`(action.getWaterTemperature()).thenReturn("25.0")
        dataSource.getWaterTemp()
        Mockito.verify(action, Mockito.times(1)).getWaterTemperature()
    }

    @Test
    @DisplayName("When asking the temp to the external data source, then value from the action is returned")
    fun returnActionValueWhenRequestTheTempToTheExternalDataSource() {
        Mockito.`when`(action.getWaterTemperature()).thenReturn("25.0")
        assertTrue(dataSource.getWaterTemp() == 25.0)

    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}