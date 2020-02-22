package showBoxHumidityFeature.domain

import showBoxHumidityFeature.action.UpdateBoxHumidityActionImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class UpdateBoxHumidityUseCaseTest{

    private val action = Mockito.mock(UpdateBoxHumidityActionImpl::class.java)
    private val useCase = UpdateBoxHumidityUseCase(action)

    @Test
    @DisplayName("Should execute the update action")
    fun updateLcdInformationTest() {
        useCase.updateBoxHumidity()
        Mockito.verify(action, Mockito.times(1)).updateBoxHumidity()
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}