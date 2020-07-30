package showBoxHumidityFeature.domain

import application.logger.LoggerWrapper
import collectHumidityAndBoxTempFeature.domain.contracts.HumidityAndTemperatureRepository
import collectHumidityAndBoxTempFeature.domain.exceptions.HumiditySensorException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import showBoxHumidityFeature.domain.contract.controller.ScreenController
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger

internal class UpdateBoxHumidityUseCaseTest{

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val repository = Mockito.mock(HumidityAndTemperatureRepository::class.java)
    private val screenController = Mockito.mock(ScreenController::class.java)
    private val useCase = UpdateBoxHumidityUseCase(repository, screenController, loggerWrapper)

    @Test
    @DisplayName("Repository is called when updating the humidity")
    fun repositoryIsCallWhenUpdatingHumidity() {
        useCase.updateBoxHumidity()
        Mockito.verify(repository, Mockito.times(1)).getCurrentHumidity()
    }

    @Test
    @DisplayName("The screen controller is called when updating the humidity")
    fun screenControllerIsCalledWhenUpdatingHumidity() {
        Mockito.`when`(repository.getCurrentHumidity()).thenReturn(50.0)
        useCase.updateBoxHumidity()
        Mockito.verify(screenController, Mockito.times(1)).
            printBoxHumidity(50.0, buildDate())
    }

    private fun buildDate(): String {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"))
        return SimpleDateFormat("HH:mm  dd MMM yyyy").format(cal.time)
    }

    @Test
    @DisplayName("When exception is thrown we log a warning")
    fun warningIsLoggedWhenExceptionIsThrown() {
        Mockito.`when`(repository.getCurrentHumidity()).thenAnswer {
            throw HumiditySensorException("The humidity is 0.0")
        }
        useCase.updateBoxHumidity()
        val className = useCase::class.java.simpleName
        Mockito.verify(logger, Mockito.times(1)).
            warning("$className: There were an error when trying to update the box humidity")
    }

    @Test
    @DisplayName("When updated box humidity, we log the success ")
    fun logSuccessWhenCorrectlyUpdatedTheScreen() {
        Mockito.`when`(repository.getCurrentHumidity()).thenReturn(50.0)
        Mockito.`when`(screenController.printBoxHumidity(50.0, buildDate())).thenReturn(true)
        useCase.updateBoxHumidity()
        val className = useCase::class.java.simpleName
        Mockito.verify(logger, Mockito.times(1)).
            info("$className: The box humidity was successfully updated")
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}