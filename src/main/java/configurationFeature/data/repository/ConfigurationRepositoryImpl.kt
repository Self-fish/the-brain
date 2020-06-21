package configurationFeature.data.repository

import application.logger.LoggerWrapper
import configurationFeature.data.action.UpdateConfigurationAction
import configurationFeature.domain.contracts.repository.ConfigurationRepository
import java.util.*
import kotlin.math.roundToLong

class ConfigurationRepositoryImpl(private val logger: LoggerWrapper,
    private val action: UpdateConfigurationAction) : ConfigurationRepository {

    companion object {
        private const val MADRID_TIMEZONE = "Europe/Madrid"
    }

    private fun getCurrentTime(): Long {
        val utcMillis = System.currentTimeMillis()
        val currentTime = utcMillis + TimeZone.getTimeZone(MADRID_TIMEZONE).getOffset(utcMillis)
        val roundedTime = (currentTime / 1000).toDouble().roundToLong()
        logger.fine(this::class.simpleName, "The current time is: $roundedTime")
        return roundedTime
    }

    override fun sendCurrentTime(): Boolean {
        return action.sendCurrentTime(getCurrentTime())
    }
}