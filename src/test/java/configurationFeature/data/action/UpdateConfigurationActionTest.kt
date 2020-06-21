package configurationFeature.data.action

import application.logger.LoggerWrapper
import application.usb.UsbController
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import java.util.logging.Logger

internal class UpdateConfigurationActionTest {

    companion object {
        const val MOCK_CURRENT_TIME = 1580640454L
        const val ACTION = "CON"
    }

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val usbController = Mockito.mock(UsbController::class.java)
    private val action = UpdateConfigurationAction(loggerWrapper, usbController)

    @Test
    @DisplayName("When executing the action, the commands are sent and read")
    fun updateConfigurationLessThan15MinutesAgo() {
        action.sendCurrentTime(MOCK_CURRENT_TIME)
        Mockito.verify(usbController, Mockito.times(1)).
                sendCommand("$ACTION:$MOCK_CURRENT_TIME")
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}