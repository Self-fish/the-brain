package lightsFeature.data.action

import BaseAction
import application.logger.LoggerWrapper
import application.usb.UsbController
import lightsFeature.domain.contracts.action.LightAction
import lightsFeature.domain.model.LightStatus
import org.koin.standalone.KoinComponent

open class LightActionImpl(logger: LoggerWrapper, usbController: UsbController) :
        KoinComponent, LightAction, BaseAction(logger, usbController) {

    private val LIGHTS_ON = "L_N"
    private val LIGHTS_OFF = "L_F"
    private val GET_LIGHTS = "L_G"

    override fun turnOnLights(currentLightStatus: LightStatus): Boolean {
        if(currentLightStatus != LightStatus.ON) {
            if(executeAction(LIGHTS_ON) == "OK") {
                return true
            }
        }
        return false
    }

    override fun turnOffLights(currentLightStatus: LightStatus): Boolean {
        if(currentLightStatus != LightStatus.OFF) {
            if (executeAction(LIGHTS_OFF) == "OK") {
                return true
            }
        }

        return false
    }

    override fun getLightStatus(): String {
        return executeAction(GET_LIGHTS)
    }
}