package showBoxHumidityFeature.domain

import application.logger.LoggerWrapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.logging.Logger

internal class UpdateBoxHumidityUseCaseTest{

    /*companion object {
        const val SUCCESS_LOG = "The box humidity was successfully updated"
        const val ERROR_LOG = "There were an error when trying to update the box humidity"
    }


    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val action = Mockito.mock(UpdateBoxHumidityActionImpl::class.java)
    private val useCase = UpdateBoxHumidityUseCase(action, loggerWrapper)

    @Test
    @DisplayName("The success action is correctly tracked when success")
    fun updateLcdInformationSuccessTest() {
        Mockito.`when`(action.updateBoxHumidity()).thenReturn(true)
        useCase.updateBoxHumidity()
        val className = useCase::class.simpleName
        Mockito.verify(logger, Mockito.times(1)).info("$className: $SUCCESS_LOG")
    }

    @Test
    @DisplayName("The success action is correctly tracked when fail")
    fun updateLcdInformationFaileTest() {
        Mockito.`when`(action.updateBoxHumidity()).thenReturn(false)
        useCase.updateBoxHumidity()
        val className = useCase::class.simpleName
        Mockito.verify(logger, Mockito.times(1)).warning("$className: $ERROR_LOG")
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }*/


}