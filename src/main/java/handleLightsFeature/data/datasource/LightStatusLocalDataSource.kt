package handleLightsFeature.data.datasource

import application.logger.LoggerWrapper
import handleLightsFeature.domain.model.LightStatus

open class LightStatusLocalDataSource(private val logger: LoggerWrapper) {

    open var currentLightStatus : LightStatus = LightStatus.OFF
    open var cacheTimeStamp : Long = 0

    fun updateCache(lightStatus: LightStatus) {
        logger.fine(this::class.simpleName, "Updating the lights cache to $lightStatus")
        currentLightStatus = lightStatus
        cacheTimeStamp = System.currentTimeMillis()
    }


}