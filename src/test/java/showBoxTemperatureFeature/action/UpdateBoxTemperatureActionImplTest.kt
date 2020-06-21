package showBoxTemperatureFeature.action

import application.usb.UsbController
import application.logger.LoggerWrapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.logging.Logger

internal class UpdateBoxTemperatureActionImplTest {

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val usbController = Mockito.mock(UsbController::class.java)
    private val action = UpdateBoxTemperatureActionImpl(loggerWrapper, usbController)
    private val TEMP_SCREEN_UPDATE = "T_U"

    @Test
    @DisplayName("When the action was executed more than 15 seconds ago then we should send the command and wait for the answer")
    fun updateLcdInformationTestMoreThan15SecondsAgo() {
        action.updateBoxTemperature()
        Mockito.verify(usbController, Mockito.times(1)).sendCommand(TEMP_SCREEN_UPDATE)
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}


