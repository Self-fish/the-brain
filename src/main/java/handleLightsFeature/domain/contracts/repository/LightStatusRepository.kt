package handleLightsFeature.domain.contracts.repository

import handleLightsFeature.domain.model.LightStatus

interface LightStatusRepository {

    fun updateLightStatus(status: LightStatus): Boolean
}