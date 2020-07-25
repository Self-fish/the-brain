package showBoxHumidityFeature.domain

import application.logger.LoggerWrapper
import org.koin.standalone.KoinComponent
import showBoxHumidityFeature.domain.contract.repository.BoxHumidityRepository
import showBoxHumidityFeature.domain.exceptions.HumiditySensorException

class UpdateBoxHumidityUseCase(private val repository: BoxHumidityRepository,
                               private val logger: LoggerWrapper) : KoinComponent  {

    fun updateBoxHumidity() {
        try {
            println("Use case: Intentamos leer humedad")
            println(repository.getBoxHumidity())
            /*if(screenController.printBoxHumidity(repository.getBoxHumidity())){
                logger.info(this::class.simpleName, "The box humidity was successfully updated")
            }*/
        } catch (e: HumiditySensorException) {
            logger.warning(this::class.simpleName, "There were an error when trying to update the " +
                    "box humidity")
        }

    }
}