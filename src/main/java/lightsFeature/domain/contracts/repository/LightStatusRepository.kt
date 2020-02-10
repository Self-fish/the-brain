package lightsFeature.domain.contracts.repository

import lightsFeature.domain.model.LightStatus

interface LightStatusRepository {

    fun getLightStatus() : LightStatus
    fun updateLightStatus(status: LightStatus)
}