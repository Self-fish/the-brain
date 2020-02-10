package lightsFeature

import lightsFeature.data.action.LightActionImpl
import lightsFeature.data.datasource.LightPreferencesLocalDataSource
import lightsFeature.data.datasource.LightStatusExternalDataSource
import lightsFeature.data.datasource.LightStatusLocalDataSource
import lightsFeature.data.repository.LightPreferencesRepositoryImpl
import lightsFeature.data.repository.LightStatusRepositoryImpl
import lightsFeature.domain.HandleLightsUseCase
import lightsFeature.domain.contracts.action.LightAction
import lightsFeature.domain.contracts.repository.LightPreferencesRepository
import lightsFeature.domain.contracts.repository.LightStatusRepository
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