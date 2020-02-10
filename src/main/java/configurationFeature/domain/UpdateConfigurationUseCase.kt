package configurationFeature.domain

import configurationFeature.domain.contracts.action.UpdateConfigurationAction
import configurationFeature.domain.contracts.repository.ConfigurationRepository
import org.koin.standalone.KoinComponent

class UpdateConfigurationUseCase(val action: UpdateConfigurationAction,
                                 val repository: ConfigurationRepository) : KoinComponent {

    fun updateConfiguration() {
        action.updateConfiguration(repository.getCurrentTime())
    }

}