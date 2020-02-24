package handleWaterTempFeature

import handleWaterTempFeature.data.action.WaterTempAction
import handleWaterTempFeature.data.datasource.WaterTempExternalDataSource
import handleWaterTempFeature.data.datasource.WaterTempLocalDataSource
import handleWaterTempFeature.data.repository.HeaterStatusRepositoryImpl
import handleWaterTempFeature.data.repository.TemperatureRepositoryImpl
import handleWaterTempFeature.data.repository.WaterTempPreferencesRepositoryImpl
import handleWaterTempFeature.domain.HandleWaterTempUseCase
import handleWaterTempFeature.domain.contracts.repository.HeaterStatusRepository
import handleWaterTempFeature.domain.contracts.repository.TemperatureRepository
import handleWaterTempFeature.domain.contracts.repository.WaterTempPreferencesRepository
import org.koin.dsl.module.module

val handleWaterTempModule = module {

    single {WaterTempAction(get(), get())}
    single<HeaterStatusRepository> { HeaterStatusRepositoryImpl(get()) }
    single<TemperatureRepository> { TemperatureRepositoryImpl(get(), get()) }
    single { WaterTempExternalDataSource(get()) }
    single { WaterTempLocalDataSource() }
    single<WaterTempPreferencesRepository> { WaterTempPreferencesRepositoryImpl() }
    single {HandleWaterTempUseCase(get(), get(), get())}

}