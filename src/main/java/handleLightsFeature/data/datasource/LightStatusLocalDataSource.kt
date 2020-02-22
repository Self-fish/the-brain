package handleLightsFeature.data.datasource

import handleLightsFeature.domain.model.LightStatus

open class LightStatusLocalDataSource {

    open var currentLightStatus : LightStatus = LightStatus.OFF
    open var cacheTimeStamp : Long = 0

    fun updateCache(lightStatus: LightStatus) {
        currentLightStatus = lightStatus
        cacheTimeStamp = System.currentTimeMillis()
    }


}