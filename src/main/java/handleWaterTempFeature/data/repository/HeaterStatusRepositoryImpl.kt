package handleWaterTempFeature.data.repository

import handleWaterTempFeature.data.action.WaterTempAction
import handleWaterTempFeature.domain.contracts.repository.HeaterStatusRepository
import handleWaterTempFeature.domain.model.HeaterStatus
import org.koin.standalone.KoinComponent

class HeaterStatusRepositoryImpl(private val action: WaterTempAction) : HeaterStatusRepository, KoinComponent {

    override fun updateHeaterStatus(heaterStatus: HeaterStatus) {
        when (heaterStatus) {
            HeaterStatus.ON -> {
                action.turnOnHeater()
            }
            HeaterStatus.OFF -> {
                action.turnOffHeater()
            }
        }
    }

}