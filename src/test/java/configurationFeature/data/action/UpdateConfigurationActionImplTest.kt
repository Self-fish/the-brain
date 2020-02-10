package configurationFeature.data.action

import application.logger.LoggerWrapper
import application.usb.UsbController
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import java.util.logging.Logger

internal class UpdateConfigurationActionImplTest {

    companion object {
        const val MOCK_CURRENT_TIME = 1580640454L
        const val ACTION = "CON"
    }

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val usbController = Mockito.mock(UsbController::class.java)
    private val action = UpdateConfigurationActionImpl(loggerWrapper, usbController)

    @Test
    @DisplayName("When the last update was done less than 15 minutes ago, then the action should not be executed")
    fun updateConfigurationLessThan15MinutesAgo() {
        action.lastUpdate = 1580640450L
        action.updateConfiguration(MOCK_CURRENT_TIME)
        Mockito.verify(usbController, Mockito.times(0)).
                sendCommand("$ACTION:$MOCK_CURRENT_TIME")
        Mockito.verify(usbController, Mockito.times(0)).readCommand()
    }

    @Test
    @DisplayName("When the last update was done more than 15 minutes ago, then the action should not be executed")
    fun updateConfigurationMoreThan15MinutesAgo() {
        action.lastUpdate = 1579690454L
        action.updateConfiguration(MOCK_CURRENT_TIME)
        Mockito.verify(usbController, Mockito.times(1)).
                sendCommand("$ACTION:$MOCK_CURRENT_TIME")
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}