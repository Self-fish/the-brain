package showBoxHumidityFeature.domain

import application.logger.LoggerWrapper
import showBoxHumidityFeature.domain.contract.UpdateBoxHumidityAction
import org.koin.standalone.KoinComponent

class UpdateBoxHumidityUseCase(private val action : UpdateBoxHumidityAction,
    private val logger: LoggerWrapper) : KoinComponent  {

    fun updateBoxHumidity() {
        if(action.updateBoxHumidity()) {
            logger.info(this::class.simpleName, "The box humidity was successfully updated")
        } else {
            logger.warning(this::class.simpleName, "There were an error when trying to update the " +
                    "box humidity")
        }
    }
}