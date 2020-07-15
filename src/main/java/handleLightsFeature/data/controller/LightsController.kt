package handleLightsFeature.data.controller

import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin
import handleLightsFeature.domain.model.LightStatus

class LightsController {

    private val lightRelay = GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.HIGH)

    fun updateLightStatus(status: LightStatus) {
        when (status) {
            LightStatus.ON -> lightRelay.state = PinState.LOW
            LightStatus.OFF -> lightRelay.state == PinState.HIGH
        }
    }

    fun getCurrentLightStatus() : LightStatus {
        return when (lightRelay.state) {
            PinState.LOW -> {
                LightStatus.ON
            }
            else -> LightStatus.OFF
        }
    }

}
