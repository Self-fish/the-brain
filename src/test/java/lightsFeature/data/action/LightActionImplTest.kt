package lightsFeature.data.action

import application.logger.LoggerWrapper
import application.usb.UsbController
import lightsFeature.domain.model.LightStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import org.mockito.Mockito
import java.util.logging.Logger
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class LightActionImplTest {

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val usbController = Mockito.mock(UsbController::class.java)
    private val action = LightActionImpl(loggerWrapper, usbController)
    private val LIGHTS_ON = "L_ON"
    private val LIGHTS_OFF = "L_OFF"
    private val GET_LIGHTS = "L_G"

    @Test
    @DisplayName("If the Lights are ON, we should not send the action to turn ON")
    fun turnOnLightsWhenAreON() {
        action.turnOnLights(LightStatus.ON)
        Mockito.verify(usbController, Mockito.times(0)).sendCommand(LIGHTS_ON)
        Mockito.verify(usbController, Mockito.times(0)).readCommand()
    }

    @Test
    @DisplayName("If the Lights are OFF, we should send the action to turn ON")
    fun turnOnLightsWhenAreOFF() {
        action.turnOnLights(LightStatus.OFF)
        Mockito.verify(usbController, Mockito.times(1)).sendCommand(LIGHTS_ON)
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @Test
    @DisplayName("If the Lights are OFF, we should not send the action to turn OFF")
    fun turnOFFLightsWhenAreOFF() {
        action.turnOffLights(LightStatus.OFF)
        Mockito.verify(usbController, Mockito.times(0)).sendCommand(LIGHTS_OFF)
        Mockito.verify(usbController, Mockito.times(0)).readCommand()
    }

    @Test
    @DisplayName("If the Lights are ON, we should send the action to turn OFF")
    fun turnOFFLightsWhenAreON() {
        action.turnOffLights(LightStatus.ON)
        Mockito.verify(usbController, Mockito.times(1)).sendCommand(LIGHTS_OFF)
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @Test
    @DisplayName("When request the LightStatus, I should send a command and wait the response")
    fun getLightStatus() {
        action.getLightStatus()
        Mockito.verify(usbController, Mockito.times(1)).sendCommand(GET_LIGHTS)
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @Test
    @DisplayName("When the server return OK, then a True value is returned when switch ON")
    fun returnTrueIfActionONIsExecuted() {
        Mockito.`when`(usbController.readCommand()).thenReturn("OK")
        assertTrue(action.turnOnLights(LightStatus.OFF))
    }

    @Test
    @DisplayName("When the server return KO, then a False value is returned when switch ON")
    fun returnFalseIfActionONIsNotExecuted() {
        Mockito.`when`(usbController.readCommand()).thenReturn("KO")
        assertFalse(action.turnOnLights(LightStatus.OFF))
    }

    @Test
    @DisplayName("When the server return OK, then a True value is returned when switch OFF")
    fun returnTrueIfActionOFFIsExecuted() {
        Mockito.`when`(usbController.readCommand()).thenReturn("OK")
        assertTrue(action.turnOffLights(LightStatus.ON))
    }

    @Test
    @DisplayName("When the server return KO, then a False value is returned when switch OFF")
    fun returnFalseIfActionOFFIsNotExecuted() {
        Mockito.`when`(usbController.readCommand()).thenReturn("KO")
        assertFalse(action.turnOffLights(LightStatus.ON))
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}