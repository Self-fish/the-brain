package handleLightsFeature.data.controller

import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.PinState
import handleLightsFeature.domain.model.LightStatus

open class LightsController(private val lightRelay: GpioPinDigitalOutput) {

    open fun updateLightStatus(status: LightStatus) {
        when (status) {
            LightStatus.ON -> {
                lightRelay.state = PinState.LOW
            }
            LightStatus.OFF -> {
                lightRelay.state = PinState.HIGH
            }
        }
    }

    open fun getCurrentLightStatus() : LightStatus {
        return when (lightRelay.state) {
            PinState.LOW -> {
                LightStatus.ON
            }
            else -> {
                LightStatus.OFF
            }
        }
    }

}
