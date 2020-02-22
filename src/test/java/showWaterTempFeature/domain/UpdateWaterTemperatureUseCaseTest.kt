package showWaterTempFeature.domain

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import showWaterTempFeature.action.UpdateWaterTemperatureActionImpl

internal class UpdateWaterTemperatureUseCaseTest{

    private val action = Mockito.mock(UpdateWaterTemperatureActionImpl::class.java)
    private val useCase = UpdateWaterTemperatureUseCase(action)

    @Test
    @DisplayName("Should execute the update action")
    fun updateLcdInformationTest() {
        useCase.updateWaterTemperature()
        Mockito.verify(action, Mockito.times(1)).updateWaterTemperature()
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}