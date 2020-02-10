package configurationFeature

import configurationFeature.data.action.UpdateConfigurationActionImpl
import configurationFeature.data.repository.ConfigurationRepositoryImpl
import configurationFeature.domain.UpdateConfigurationUseCase
import configurationFeature.domain.contracts.action.UpdateConfigurationAction
import configurationFeature.domain.contracts.repository.ConfigurationRepository
import org.koin.dsl.module.module

val configurationModule = module {

    single<UpdateConfigurationAction> { UpdateConfigurationActionImpl(get(), get()) }
    single<ConfigurationRepository> {ConfigurationRepositoryImpl()}
    single {UpdateConfigurationUseCase(get(), get())}


}