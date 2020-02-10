package waterTemperatureFeature.domain

import org.koin.standalone.KoinComponent
import waterTemperatureFeature.domain.contract.UpdateWaterTemperatureAction

class UpdateWaterTemperatureUseCase(private val action : UpdateWaterTemperatureAction) : KoinComponent  {

    fun updateWaterTemperature() {
        action.updateWaterTemperature()
    }
}