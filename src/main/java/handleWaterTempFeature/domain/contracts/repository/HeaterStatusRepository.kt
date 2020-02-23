package handleWaterTempFeature.domain.contracts.repository

import handleWaterTempFeature.domain.model.HeaterStatus

interface HeaterStatusRepository {

    fun updateHeaterStatus(heaterStatus: HeaterStatus)
}