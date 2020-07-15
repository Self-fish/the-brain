package handleLightsFeature.data.controller

import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.PinState
import handleLightsFeature.domain.model.LightStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import kotlin.test.assertEquals

internal class LightsControllerTest {

    private val lightRelay = Mockito.mock(GpioPinDigitalOutput::class.java)
    private val controller = LightsController(lightRelay)

    @Test
    @DisplayName("Relay set to Low when the status is ON")
    fun relaySetToLOWWhenStatusIsON() {
        controller.updateLightStatus(LightStatus.ON)
        Mockito.verify(lightRelay, Mockito.times(1)).state = PinState.LOW
    }

    @Test
    @DisplayName("Relay set to HIGH when the status is OFF")
    fun relaySetToHIGHWhenStatusIsOFF() {
        controller.updateLightStatus(LightStatus.OFF)
        Mockito.verify(lightRelay, Mockito.times(1)).state = PinState.HIGH
    }

    @Test
    @DisplayName("When the lights are ON we return ON Status")
    fun returnONStatusWhenLightsAreON() {
        Mockito.`when`(lightRelay.state).thenReturn(PinState.LOW)
        assertEquals(controller.getCurrentLightStatus(), LightStatus.ON)
    }

    @Test
    @DisplayName("When the lights are OFF we return OFF Status")
    fun returnOFFStatusWhenLightsAreOFF() {
        Mockito.`when`(lightRelay.state).thenReturn(PinState.HIGH)
        assertEquals(controller.getCurrentLightStatus(), LightStatus.OFF)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}