package showWaterTempFeature.domain

import application.logger.LoggerWrapper
import org.koin.standalone.KoinComponent
import showWaterTempFeature.domain.contract.UpdateWaterTemperatureAction

class UpdateWaterTemperatureUseCase(private val action : UpdateWaterTemperatureAction,
                                    private val logger: LoggerWrapper) : KoinComponent  {

    fun updateWaterTemperature() {
       if(action.updateWaterTemperature()) {
           logger.info(this::class.simpleName, "The Water Temperature was successfully updated")
       } else {
           logger.warning(this::class.simpleName, "There were a problem updating the Water temperature")
       }
    }
}