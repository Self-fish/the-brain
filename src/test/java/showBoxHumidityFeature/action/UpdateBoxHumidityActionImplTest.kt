package showBoxHumidityFeature.action

import application.usb.UsbController
import application.logger.LoggerWrapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.logging.Logger

internal class UpdateBoxHumidityActionImplTest {

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val usbController = Mockito.mock(UsbController::class.java)
    private val action = UpdateBoxHumidityActionImpl(loggerWrapper, usbController)
    private val HUMIDITY_SCREEN_UPDATE = "H_U"

    @Test
    @DisplayName("When executing the action we send the command and wait to read the answer")
    fun updateLcdInformationSendCommandAndReadTheAnswer() {
        action.updateBoxHumidity()
        Mockito.verify(usbController, Mockito.times(1)).sendCommand(HUMIDITY_SCREEN_UPDATE)
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}


