package configurationFeature.domain

import application.logger.LoggerWrapper
import configurationFeature.domain.contracts.repository.ConfigurationRepository
import org.koin.standalone.KoinComponent

class UpdateConfigurationUseCase(val repository: ConfigurationRepository,
                                 private val logger: LoggerWrapper) : KoinComponent {

    fun updateConfiguration() {
        if(repository.sendCurrentTime()) {
            logger.info(this::class.simpleName, "Time updated successfully")
        } else {
            logger.warning(this::class.simpleName, "Something went wrong updating the time")
        }
    }

}