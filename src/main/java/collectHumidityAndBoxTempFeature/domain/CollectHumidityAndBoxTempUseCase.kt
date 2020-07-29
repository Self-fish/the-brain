package collectHumidityAndBoxTempFeature.domain

import application.logger.LoggerWrapper
import collectHumidityAndBoxTempFeature.domain.contracts.HumidityAndTemperatureRepository

class CollectHumidityAndBoxTempUseCase(private val repository: HumidityAndTemperatureRepository,
                                       private val logger: LoggerWrapper) {

    fun collectTemperatureAndHumidity() {
        if(repository.collect()){
            logger.info(this::class.java.simpleName, "The temperature and humidity inside of the box" +
                    "was successfully collected")
        } else {
            logger.severe(this::class.java.simpleName, "It was impossible to collect the temperature and " +
                    "the humidity inside of the box")
        }
    }

}