package configurationFeature.data.repository

import configurationFeature.domain.contracts.repository.ConfigurationRepository
import kotlin.math.roundToLong

class ConfigurationRepositoryImpl : ConfigurationRepository {

    override fun getCurrentTime(): Long {
        val currentTime = System.currentTimeMillis() + 3600000
        return (currentTime / 1000).toDouble().roundToLong()
    }
}