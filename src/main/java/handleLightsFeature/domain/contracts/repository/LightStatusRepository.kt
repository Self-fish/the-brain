package handleLightsFeature.domain.contracts.repository

import handleLightsFeature.domain.model.LightStatus

interface LightStatusRepository {

    fun getLightStatus() : LightStatus
    fun updateLightStatus(status: LightStatus)
}