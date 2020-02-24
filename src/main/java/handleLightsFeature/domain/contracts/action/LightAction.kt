package handleLightsFeature.domain.contracts.action

import handleLightsFeature.domain.model.LightStatus

interface LightAction {

    fun turnOnLights(currentLightStatus: LightStatus) : Boolean
    fun turnOffLights(currentLightStatus: LightStatus) : Boolean
    fun getLightStatus(): String

}