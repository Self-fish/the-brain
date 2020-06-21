package handleLightsFeature.domain

import application.logger.LoggerWrapper
import handleLightsFeature.domain.contracts.repository.LightPreferencesRepository
import handleLightsFeature.domain.contracts.repository.LightStatusRepository
import handleLightsFeature.domain.contracts.action.LightAction
import handleLightsFeature.domain.model.LightPreferences
import handleLightsFeature.domain.model.LightStatus
import org.koin.standalone.KoinComponent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HandleLightsUseCase(private val lightStatusRepository: LightStatusRepository,
                          private val lightPreferencesRepository: LightPreferencesRepository,
                          private val lightAction: LightAction,
                          private val logger: LoggerWrapper) : KoinComponent{

    fun handleLights() {
        val currentLightStatus = lightStatusRepository.getLightStatus()
        val lightPreferences = lightPreferencesRepository.getLightsPreferences()
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))

        if(shouldTurnOnLights(currentTime, lightPreferences)) {
            if(lightAction.turnOnLights(currentLightStatus)) {
                logger.info(this::class.simpleName, "Turning lights ON")
                lightStatusRepository.updateLightStatus(LightStatus.ON)
            }
        } else {
            if(lightAction.turnOffLights(currentLightStatus)) {
                logger.info(this::class.simpleName, "Turning lights OFF")
                lightStatusRepository.updateLightStatus(LightStatus.OFF)
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