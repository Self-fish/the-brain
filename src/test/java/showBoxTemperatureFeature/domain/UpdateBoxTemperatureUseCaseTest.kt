package showBoxTemperatureFeature.domain

import showBoxTemperatureFeature.action.UpdateBoxTemperatureActionImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class UpdateBoxTemperatureUseCaseTest{

    private val action = Mockito.mock(UpdateBoxTemperatureActionImpl::class.java)
    private val useCase = UpdateBoxTemperatureUseCase(action)

    @Test
    @DisplayName("Should execute the update action")
    fun updateLcdInformationTest() {
        useCase.updateBoxTemperature()
        Mockito.verify(action, Mockito.times(1)).updateBoxTemperature()
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}