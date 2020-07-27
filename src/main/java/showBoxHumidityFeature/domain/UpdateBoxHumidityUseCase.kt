package showBoxHumidityFeature.domain

import application.logger.LoggerWrapper
import collectHumidityFeature.domain.contracts.HumidityAndTemperatureRepository
import org.koin.standalone.KoinComponent
import showBoxHumidityFeature.domain.contract.controller.ScreenController
import collectHumidityFeature.domain.exceptions.HumiditySensorException

class UpdateBoxHumidityUseCase(private val repository: HumidityAndTemperatureRepository,
                               private val screenController: ScreenController,
                               private val logger: LoggerWrapper) : KoinComponent  {

    fun updateBoxHumidity() {
        try {
            if(screenController.printBoxHumidity(repository.getCurrentHumidity())){
                logger.info(this::class.simpleName, "The box humidity was successfully updated")
            }
        } catch (e: HumiditySensorException) {
            logger.warning(this::class.simpleName, "There were an error when trying to update the " +
                    "box humidity")
        }

    }
}