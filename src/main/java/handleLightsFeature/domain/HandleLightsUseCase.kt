package handleLightsFeature.domain

import application.logger.LoggerWrapper
import handleLightsFeature.domain.contracts.repository.LightPreferencesRepository
import handleLightsFeature.domain.contracts.repository.LightStatusRepository
import handleLightsFeature.domain.model.LightPreferences
import handleLightsFeature.domain.model.LightStatus
import org.koin.standalone.KoinComponent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HandleLightsUseCase(private val lightStatusRepository: LightStatusRepository,
                          private val lightPreferencesRepository: LightPreferencesRepository,
                          private val logger: LoggerWrapper) : KoinComponent{

    fun handleLights() {
        val lightPreferences = lightPreferencesRepository.getLightsPreferences()
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))

        if(shouldTurnOnLights(currentTime, lightPreferences)) {
            if(lightStatusRepository.updateLightStatus(LightStatus.ON)) {
                logger.info(this::class.simpleName, "Turning lights ON")
            }

        } else {
            if(lightStatusRepository.updateLightStatus(LightStatus.OFF)) {
                logger.info(this::class.simpleName, "Turning lights OFF")
            }
        }
    }

    fun shouldTurnOnLights(currentTime: String, lightPreferences: LightPreferences) : Boolean {
        return when {
            currentTime >= lightPreferences.startingHour && currentTime < lightPreferences.finishingHour -> {
                logger.fine(this::class.simpleName, "The lights should be ON")
                true
            }
            else -> {
                logger.fine(this::class.simpleName, "The lights should be OFF")
                false
            }
        }
    }
}