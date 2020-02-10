package lightsFeature.data.repository

import lightsFeature.data.datasource.LightStatusExternalDataSource
import lightsFeature.data.datasource.LightStatusLocalDataSource
import lightsFeature.domain.contracts.repository.LightStatusRepository
import lightsFeature.domain.model.LightStatus
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