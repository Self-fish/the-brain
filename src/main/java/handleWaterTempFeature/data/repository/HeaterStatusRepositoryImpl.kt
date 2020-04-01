package handleWaterTempFeature.data.repository

import handleWaterTempFeature.data.action.WaterTempAction
import handleWaterTempFeature.domain.contracts.repository.HeaterStatusRepository
import handleWaterTempFeature.domain.model.HeaterStatus
import org.koin.standalone.KoinComponent

class HeaterStatusRepositoryImpl(private val action: WaterTempAction) : HeaterStatusRepository, KoinComponent {

    private val CACHE_TIME_TO_LIVE = 300000 //5 minute

    open var lastUpdateTimeStamp = 0L

    override fun updateHeaterStatus(heaterStatus: HeaterStatus) {
        if(shouldUpdateHeaterStatus()) {
            when (heaterStatus) {
                HeaterStatus.ON -> {
                    action.turnOnHeater()
                }
                HeaterStatus.OFF -> {
                    action.turnOffHeater()
                }
            }
            lastUpdateTimeStamp = System.currentTimeMillis()
        }
    }

    private fun shouldUpdateHeaterStatus() = System.currentTimeMillis() > lastUpdateTimeStamp + CACHE_TIME_TO_LIVE

}