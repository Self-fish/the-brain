package handleLightsFeature.data.repository

import application.logger.LoggerWrapper
import handleLightsFeature.data.controller.LightsController
import handleLightsFeature.domain.contracts.repository.LightStatusRepository
import handleLightsFeature.domain.model.LightStatus
import org.koin.standalone.KoinComponent

class LightStatusRepositoryImpl(private val logger: LoggerWrapper,
                                private val lightsController: LightsController) :
        KoinComponent, LightStatusRepository {

    private val CACHE_TIME_TO_LIVE = 900000 //15 minutes
    private var currentLightStatus : LightStatus = LightStatus.OFF
    private var cacheTimeStamp : Long = 0

    private fun currentLightStatus(): LightStatus {
        if(!isLocalCacheValid()) {
            logger.fine(this::class.simpleName, "The cache is not valid anymore. Asking for the current " +
                    "light status")
            updateCache(lightsController.getCurrentLightStatus())
        }
        return currentLightStatus
    }

    private fun updateCache(lightStatus: LightStatus) {
        logger.fine(this::class.simpleName, "Updating the lights cache to $lightStatus")
        currentLightStatus = lightStatus
        cacheTimeStamp = System.currentTimeMillis()
    }

    override fun updateLightStatus(status: LightStatus) : Boolean {
        if(currentLightStatus() != status) {
            lightsController.updateLightStatus(status)
            updateCache(status)
            logger.fine(this::class.simpleName, "Turning lights $status")
            return true
        }
        logger.fine(this::class.simpleName, "The light were already $status. Doing nothing")
        return false;
    }

    fun isLocalCacheValid() = System.currentTimeMillis() - cacheTimeStamp < CACHE_TIME_TO_LIVE

}