package showWaterTempFeature.domain

import org.koin.standalone.KoinComponent
import showWaterTempFeature.domain.contract.UpdateWaterTemperatureAction

class UpdateWaterTemperatureUseCase(private val action : UpdateWaterTemperatureAction) : KoinComponent  {

    fun updateWaterTemperature() {
        action.updateWaterTemperature()
    }
}