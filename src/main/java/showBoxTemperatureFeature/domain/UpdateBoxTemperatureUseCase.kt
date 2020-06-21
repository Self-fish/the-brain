package showBoxTemperatureFeature.domain

import application.logger.LoggerWrapper
import showBoxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction
import org.koin.standalone.KoinComponent

class UpdateBoxTemperatureUseCase(private val action : UpdateBoxTemperatureAction,
                                  private val logger: LoggerWrapper) : KoinComponent  {

    fun updateBoxTemperature() {
        if(action.updateBoxTemperature()) {
            logger.info(this::class.simpleName, "The Box Temperature was successfully updated")
        } else {
            logger.warning(this::class.simpleName, "There were a problem updating the Box temperature")
        }
    }
}