package configurationFeature.data.repository

import configurationFeature.domain.contracts.repository.ConfigurationRepository
import java.util.*
import kotlin.math.roundToLong

class ConfigurationRepositoryImpl : ConfigurationRepository {

    override fun getCurrentTime(): Long {
        val utcMillis = System.currentTimeMillis()
        val currentTime = utcMillis + TimeZone.getTimeZone("Europe/Madrid").getOffset(utcMillis)
        return (currentTime / 1000).toDouble().roundToLong()
    }
}