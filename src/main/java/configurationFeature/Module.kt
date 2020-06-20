package configurationFeature

import configurationFeature.data.action.UpdateConfigurationAction
import configurationFeature.data.repository.ConfigurationRepositoryImpl
import configurationFeature.domain.UpdateConfigurationUseCase
import configurationFeature.domain.contracts.repository.ConfigurationRepository
import org.koin.dsl.module.module

val configurationModule = module {

    single { UpdateConfigurationAction(get(), get()) }
    single<ConfigurationRepository> {ConfigurationRepositoryImpl(get(), get())}
    single {UpdateConfigurationUseCase(get(), get())}


}