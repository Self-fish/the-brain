package handleLightsFeature

import handleLightsFeature.data.controller.LightsController
import handleLightsFeature.data.datasource.LightPreferencesLocalDataSource
import handleLightsFeature.data.repository.LightPreferencesRepositoryImpl
import handleLightsFeature.data.repository.LightStatusRepositoryImpl
import handleLightsFeature.domain.HandleLightsUseCase
import handleLightsFeature.domain.contracts.repository.LightPreferencesRepository
import handleLightsFeature.domain.contracts.repository.LightStatusRepository
import org.koin.dsl.module.module

val lightModule = module {
    single { LightsController() }
    single { HandleLightsUseCase(get(), get(), get()) }
    single<LightStatusRepository> { LightStatusRepositoryImpl(get(), get()) }
    single<LightPreferencesRepository> { LightPreferencesRepositoryImpl(get()) }
    single { LightPreferencesLocalDataSource() }

}