package lightsFeature.data.datasource

import lightsFeature.domain.model.LightStatus

open class LightStatusLocalDataSource {

    open var currentLightStatus : LightStatus = LightStatus.OFF
    open var cacheTimeStamp : Long = 0

    fun updateCache(lightStatus: LightStatus) {
        currentLightStatus = lightStatus
        cacheTimeStamp = System.currentTimeMillis()
    }


}