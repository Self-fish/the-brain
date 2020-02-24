package handleLightsFeature.data.repository

import handleLightsFeature.data.datasource.LightStatusExternalDataSource
import handleLightsFeature.data.datasource.LightStatusLocalDataSource
import handleLightsFeature.domain.contracts.repository.LightStatusRepository
import handleLightsFeature.domain.model.LightStatus
import org.koin.standalone.KoinComponent

class LightStatusRepositoryImpl(private val localDataSource: LightStatusLocalDataSource,
                                private val externalDataSource: LightStatusExternalDataSource) :
        KoinComponent, LightStatusRepository {

    private val CACHE_TIME_TO_LIVE = 900000 //15 minutes

    override fun getLightStatus(): LightStatus {
        if(!isLocalCacheValid()) {
            localDataSource.updateCache(externalDataSource.getLightStatus())
        }
        return localDataSource.currentLightStatus
    }

    override fun updateLightStatus(status: LightStatus) {
        localDataSource.updateCache(status)
    }

    fun isLocalCacheValid() = System.currentTimeMillis() - localDataSource.cacheTimeStamp < CACHE_TIME_TO_LIVE

}