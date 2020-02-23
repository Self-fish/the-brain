package handleWaterTempFeature.data.action

import application.logger.LoggerWrapper
import application.usb.UsbController
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import java.util.logging.Logger

internal class WaterTempActionTest {

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val usbController = Mockito.mock(UsbController::class.java)
    private val action = WaterTempAction(loggerWrapper, usbController)

    private val HEATER_ON = "T_N"
    private val HEATER_OFF = "T_F"
    private val GET_WATER_TEMP = "T_G"

    @Test
    @DisplayName("When we want to turn ON the heater, then the command ON is sent and we wait the response")
    fun whenTurnOnHeaterTheCommandONIsSent() {
        action.turnOnHeater()
        Mockito.verify(usbController, Mockito.times(1)).sendCommand(HEATER_ON)
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @Test
    @DisplayName("When we want to turn ON the heater, then the command OFF is not sent")
    fun whenTurnOnHeaterTheCommandOFFIsNotSent() {
        action.turnOnHeater()
        Mockito.verify(usbController, Mockito.times(0)).sendCommand(HEATER_OFF)
    }

    @Test
    @DisplayName("When we want to turn OFF the heater, then the command OFF is sent and we wait the response")
    fun whenTurnOFFHeaterTheCommandOFFIsSent() {
        action.turnOffHeater()
        Mockito.verify(usbController, Mockito.times(1)).sendCommand(HEATER_OFF)
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @Test
    @DisplayName("When we want to turn OFF the heater, then the command ON is not sent")
    fun whenTurnOFFHeaterTheCommandONNIsNotSent() {
        action.turnOffHeater()
        Mockito.verify(usbController, Mockito.times(0)).sendCommand(HEATER_ON)
    }

    @Test
    @DisplayName("When we want to turn GET water temp, then the command GET is sent and we wait the response")
    fun whenGetWaterTempTheCommandGETIsSent() {
        action.getWaterTemperature()
        Mockito.verify(usbController, Mockito.times(1)).sendCommand(GET_WATER_TEMP)
        Mockito.verify(usbController, Mockito.times(1)).readCommand()
    }

    @Test
    @DisplayName("When we want to GET water temp, then the value is returned")
    fun whenGetWaterTempTheValueIsReturned() {
        Mockito.`when`(usbController.readCommand()).thenReturn("25.0")
        assertTrue(action.getWaterTemperature() == "25.0")
    }

    @Test
    @DisplayName("When we want to turn ON water temp,  and the response is OK, then we return true")
    fun whenTurnONTempAndOKThenTrueIsReturned() {
        Mockito.`when`(usbController.readCommand()).thenReturn("OK")
        assertTrue(action.turnOnHeater())
    }

    @Test
    @DisplayName("When we want to turn OFF water temp,  and the response is OK, then we return true")
    fun whenTurnOFFTempAndOKThenTrueIsReturned() {
        Mockito.`when`(usbController.readCommand()).thenReturn("OK")
        assertTrue(action.turnOffHeater())
    }

    @Test
    @DisplayName("When we want to turn ON water temp, and the response is KO, then we return false")
    fun whenTurnONTempAndKOThenFalseIsReturned() {
        Mockito.`when`(usbController.readCommand()).thenReturn("KO")
        assertFalse(action.turnOnHeater())
    }

    @Test
    @DisplayName("When we want to turn OFF water temp,  and the response is KO, then we return false")
    fun whenTurnOFFTempAndKOThenFalseIsReturned() {
        Mockito.`when`(usbController.readCommand()).thenReturn("KO")
        assertFalse(action.turnOffHeater())
    }
}