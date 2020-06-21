package showWaterTempFeature.action

import application.usb.UsbController
import application.logger.LoggerWrapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.logging.Logger

internal class UpdateWaterTemperatureActionImplTest {

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val usbController = Mockito.mock(UsbController::class.java)
    private val action = UpdateWaterTemperatureActionImpl(loggerWrapper, usbController)
    private val TEMP_SCREEN_UPDATE = "W_U"

    @Test
    @DisplayName("When the action was executed more than 15 seconds ago then we should send the command and wait for the answer")
    fun updateWaterTemperatureWhenLastUpdateWasMoreThan15SecondsAgo() {
        action.updateWaterTemperature()
        Mockito.verify(usbController, Mockito.times(1)).sendCommand(TEMP_SCREEN_UPDATE)
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }


    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}


