package lightsFeature.domain

import lightsFeature.domain.contracts.repository.LightPreferencesRepository
import lightsFeature.domain.contracts.repository.LightStatusRepository
import lightsFeature.domain.contracts.action.LightAction
import lightsFeature.domain.model.LightPreferences
import lightsFeature.domain.model.LightStatus
import org.koin.standalone.KoinComponent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HandleLightsUseCase(private val lightStatusRepository: LightStatusRepository,
                          private val lightPreferencesRepository: LightPreferencesRepository,
                          private val lightAction: LightAction) : KoinComponent{

    fun handleLights() {
        val currentLightStatus = lightStatusRepository.getLightStatus()
        val lightPreferences = lightPreferencesRepository.getLightsPreferences()
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))

        if(shouldTurnOnLights(currentTime, lightPreferences)) {
            if(lightAction.turnOnLights(currentLightStatus)) {
                lightStatusRepository.updateLightStatus(LightStatus.ON)
            }
        } else {
            if(lightAction.turnOffLights(currentLightStatus)) {
                lightStatusRepository.updateLightStatus(LightStatus.OFF)
            }
        }
    }

    private fun shouldTurnOnLights(currentTime: String, lightPreferences: LightPreferences) : Boolean {
        return when {
            currentTime >= lightPreferences.startingHour && currentTime < lightPreferences.finishingHour -> {
                true
            }
            else -> {
                false
            }
        }
    }
}