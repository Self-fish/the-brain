package handleAlertsFeature.data.action

import application.logger.LoggerWrapper
import application.usb.UsbController
import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.logging.Logger
import kotlin.test.assertEquals

internal class AlertsActionTest {

    private val SEND_ALERT = "S_A"
    private val LATER_RESPONSE = "LAT"
    private val OK_RESPONSE = "OK"

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val usbController = Mockito.mock(UsbController::class.java)
    private val action = AlertsAction(loggerWrapper, usbController)

    @Test
    @DisplayName("When send action then send command and read command are executed correctly")
    fun whenSendAlertThenSendCommandAndReadCommandAreExecuted() {
        val alert1 = Alert(0, "Mock Alert 1", AlertDate(1,22,0), 0)
        Mockito.`when`(usbController.readCommand()).thenReturn(LATER_RESPONSE)
        action.sendAlert(alert1)
        Mockito.verify(usbController, Mockito.times(1)).sendCommand("$SEND_ALERT:Mock Alert 1")
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @Test
    @DisplayName("When send action and the response is LAT then it's propagted correctly")
    fun whenSendAlertResponseLATIsCorrectlyPropagated() {
        val alert1 = Alert(0, "Mock Alert 1", AlertDate(1,22,0), 0)
        Mockito.`when`(usbController.readCommand()).thenReturn(LATER_RESPONSE)
        assertEquals(action.sendAlert(alert1), AlertsAction.AlertActionResponse.LAT)
    }

    @Test
    @DisplayName("When send action and the response is OK then it's propagted correctly")
    fun whenSendAlertResponseOKIsCorrectlyPropagated() {
        val alert1 = Alert(0, "Mock Alert 1", AlertDate(1,22,0), 0)
        Mockito.`when`(usbController.readCommand()).thenReturn(OK_RESPONSE)
        assertEquals(action.sendAlert(alert1), AlertsAction.AlertActionResponse.OK)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}