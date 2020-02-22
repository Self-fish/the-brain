package showBoxTemperatureFeature.domain

import showBoxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction
import org.koin.standalone.KoinComponent

class UpdateBoxTemperatureUseCase(private val action : UpdateBoxTemperatureAction) : KoinComponent  {

    fun updateBoxTemperature() {
        action.updateBoxTemperature()
    }
}