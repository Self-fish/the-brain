package boxTemperatureFeature.domain

import boxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction
import org.koin.standalone.KoinComponent

class UpdateBoxTemperatureUseCase(private val action : UpdateBoxTemperatureAction) : KoinComponent  {

    fun updateBoxTemperature() {
        action.updateBoxTemperature()
    }
}