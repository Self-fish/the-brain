package handleLightsFeature

import handleLightsFeature.data.action.LightActionImpl
import handleLightsFeature.data.datasource.LightPreferencesLocalDataSource
import handleLightsFeature.data.datasource.LightStatusExternalDataSource
import handleLightsFeature.data.datasource.LightStatusLocalDataSource
import handleLightsFeature.data.repository.LightPreferencesRepositoryImpl
import handleLightsFeature.data.repository.LightStatusRepositoryImpl
import handleLightsFeature.domain.HandleLightsUseCase
import handleLightsFeature.domain.contracts.action.LightAction
import handleLightsFeature.domain.contracts.repository.LightPreferencesRepository
import handleLightsFeature.domain.contracts.repository.LightStatusRepository
import org.koin.dsl.module.module

val lightModule = module {
    single<LightAction> { LightActionImpl(get(), get()) }
    single { HandleLightsUseCase(get(), get(), get()) }
    single<LightStatusRepository> { LightStatusRepositoryImpl(get(), get()) }
    single { LightStatusExternalDataSource(get()) }
    single { LightStatusLocalDataSource() }
    single<LightPreferencesRepository> { LightPreferencesRepositoryImpl(get()) }
    single { LightPreferencesLocalDataSource() }

}