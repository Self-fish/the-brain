package lightsFeature.domain.contracts.action

import lightsFeature.domain.model.LightStatus

interface LightAction {

    fun turnOnLights(currentLightStatus: LightStatus) : Boolean
    fun turnOffLights(currentLightStatus: LightStatus) : Boolean
    fun getLightStatus(): String

}