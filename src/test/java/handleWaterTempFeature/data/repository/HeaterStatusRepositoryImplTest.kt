package handleWaterTempFeature.data.repository

import handleWaterTempFeature.data.action.WaterTempAction
import handleWaterTempFeature.domain.model.HeaterStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class HeaterStatusRepositoryImplTest {

    private val action = Mockito.mock(WaterTempAction::class.java)
    private val repository = HeaterStatusRepositoryImpl(action)

    @Test
    @DisplayName("If the input is ON, the action execute turnON method")
    fun callTurnOnWhenTheInputIsON() {
        repository.updateHeaterStatus(HeaterStatus.ON)
        Mockito.verify(action, Mockito.times(1)).turnOnHeater()
    }

    @Test
    @DisplayName("If the input is OFF, the action execute turnOFF method")
    fun callTurnOnWhenTheInputIsOFF() {
        repository.updateHeaterStatus(HeaterStatus.OFF)
        Mockito.verify(action, Mockito.times(1)).turnOffHeater()
    }


    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }

}