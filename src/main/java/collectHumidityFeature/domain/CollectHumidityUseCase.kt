package collectHumidityFeature.domain

import application.logger.LoggerWrapper
import collectHumidityFeature.domain.contracts.HumidityAndTemperatureRepository

class CollectHumidityUseCase(private val repository: HumidityAndTemperatureRepository,
                             private val logger: LoggerWrapper) {

    fun collectTemperatureAndHumidity() {
        repository.collect()
    }

}