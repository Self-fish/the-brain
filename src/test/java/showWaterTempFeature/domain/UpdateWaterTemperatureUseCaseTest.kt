package showWaterTempFeature.domain

import application.logger.LoggerWrapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import showBoxTemperatureFeature.domain.UpdateBoxTemperatureUseCaseTest
import showWaterTempFeature.action.UpdateWaterTemperatureActionImpl
import java.util.logging.Logger

internal class UpdateWaterTemperatureUseCaseTest{

    companion object {
        private const val SUCCESS_LOG = "The Water Temperature was successfully updated"
        private const val ERROR_LOG = "There were a problem updating the Water temperature"
    }

    private val action = Mockito.mock(UpdateWaterTemperatureActionImpl::class.java)
    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val useCase = UpdateWaterTemperatureUseCase(action, loggerWrapper)

    @Test
    @DisplayName("When the action is executed then whe Log as success")
    fun updateLcdInformationSuccessTest() {
        Mockito.`when`(action.updateWaterTemperature()).thenReturn(true)
        useCase.updateWaterTemperature()
        val className = useCase::class.simpleName
        Mockito.verify(logger, Mockito.times(1)).info("$className: $SUCCESS_LOG")
    }

    @Test
    @DisplayName("When the action is executed then whe Log as Failed")
    fun updateLcdInformationFailedTest() {
        Mockito.`when`(action.updateWaterTemperature()).thenReturn(false)
        useCase.updateWaterTemperature()
        val className = useCase::class.simpleName
        Mockito.verify(logger, Mockito.times(1)).warning("$className: $ERROR_LOG")
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}