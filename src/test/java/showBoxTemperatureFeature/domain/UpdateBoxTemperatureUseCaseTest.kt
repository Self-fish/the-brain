package showBoxTemperatureFeature.domain

import application.logger.LoggerWrapper
import collectHumidityAndBoxTempFeature.domain.contracts.HumidityAndTemperatureRepository
import collectHumidityAndBoxTempFeature.domain.exceptions.BoxTemperatureSensorException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import showBoxTemperatureFeature.domain.contract.ScreenController
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger

internal class UpdateBoxTemperatureUseCaseTest{

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val repository = Mockito.mock(HumidityAndTemperatureRepository::class.java)
    private val screenController = Mockito.mock(ScreenController::class.java)
    private val useCase = UpdateBoxTemperatureUseCase(repository, screenController, loggerWrapper)

    @Test
    @DisplayName("Repository is called when updating the temperature")
    fun repositoryIsCallWhenUpdatingTemperature() {
        useCase.updateBoxTemperature()
        Mockito.verify(repository, Mockito.times(1)).getCurrentTemperature()
    }

    @Test
    @DisplayName("The screen controller is called when updating the temperature")
    fun screenControllerIsCalledWhenUpdatingTemperature() {
        Mockito.`when`(repository.getCurrentTemperature()).thenReturn(50.0)
        useCase.updateBoxTemperature()
        Mockito.verify(screenController, Mockito.times(1)).
            printBoxTemperature(50.0, buildDate())
    }

    private fun buildDate(): String {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"))
        return SimpleDateFormat("HH:mm  dd MMM yyyy").format(cal.time)
    }

    @Test
    @DisplayName("When exception is thrown we log a warning")
    fun warningIsLoggedWhenExceptionIsThrown() {
        Mockito.`when`(repository.getCurrentTemperature()).thenAnswer {
            throw BoxTemperatureSensorException("The temperature is 0.0")
        }
        useCase.updateBoxTemperature()
        val className = useCase::class.java.simpleName
        Mockito.verify(logger, Mockito.times(1)).
            warning("$className: There were an error when trying to update the box temperature")
    }

    @Test
    @DisplayName("When updated box humidity, we log the success ")
    fun logSuccessWhenCorrectlyUpdatedTheScreen() {
        Mockito.`when`(repository.getCurrentTemperature()).thenReturn(50.0)
        Mockito.`when`(screenController.printBoxTemperature(50.0, buildDate())).thenReturn(true)
        useCase.updateBoxTemperature()
        val className = useCase::class.java.simpleName
        Mockito.verify(logger, Mockito.times(1)).
        info("$className: The box temperature was successfully updated")
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}