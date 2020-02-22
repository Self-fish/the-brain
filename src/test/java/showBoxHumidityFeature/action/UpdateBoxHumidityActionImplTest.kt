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
    @DisplayName("When the last update was more than 15 seconds ago then, we should send the command and wait for the answer")
    fun updateLcdInformationWhenLastUpdateMoreThan15SecondsTest() {
        action.lastUpdate = System.currentTimeMillis() - 100000
        action.updateBoxHumidity()
        Mockito.verify(usbController, Mockito.times(1)).sendCommand(HUMIDITY_SCREEN_UPDATE)
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @Test
    @DisplayName("When the last update was less than 15 seconds ago then, we should send the command and wait for the answer")
    fun updateLcdInformationWhenLastUpdateLessThan15SecondsTest() {
        action.lastUpdate = System.currentTimeMillis() - 5000
        action.updateBoxHumidity()
        Mockito.verify(usbController, Mockito.times(0)).sendCommand(HUMIDITY_SCREEN_UPDATE)
        Mockito.verify(usbController, Mockito.times(0)).readCommand()
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}


