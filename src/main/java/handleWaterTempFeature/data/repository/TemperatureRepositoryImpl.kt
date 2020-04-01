package handleWaterTempFeature.data.repository

import handleWaterTempFeature.data.datasource.WaterTempExternalDataSource
import handleWaterTempFeature.data.datasource.WaterTempLocalDataSource
import handleWaterTempFeature.domain.contracts.repository.TemperatureRepository
import org.koin.standalone.KoinComponent

class TemperatureRepositoryImpl(private val localDataSource: WaterTempLocalDataSource,
                                private val externalDataSource: WaterTempExternalDataSource):
        TemperatureRepository, KoinComponent {

    private val CACHE_TIME_TO_LIVE = 300000 //5 minute

    override fun getCurrentTemperature(): Double {
        if(!isLocalCacheValid()) {
            localDataSource.updateCache(externalDataSource.getWaterTemp())
        }
        return localDataSource.waterTemp
    }

    private fun isLocalCacheValid() = System.currentTimeMillis() - localDataSource.lastUpdateTimeStamp < CACHE_TIME_TO_LIVE
}