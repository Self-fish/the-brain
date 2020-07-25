package showBoxHumidityFeature

import showBoxHumidityFeature.domain.UpdateBoxHumidityUseCase
import org.koin.dsl.module.module
import showBoxHumidityFeature.data.controller.DHT22Controller
import showBoxHumidityFeature.data.repository.BoxHumidityRepositoryImpl
import showBoxHumidityFeature.domain.contract.repository.BoxHumidityRepository

val boxHumidityModule = module {
    single { UpdateBoxHumidityUseCase(get(), get()) }
    single<BoxHumidityRepository> { BoxHumidityRepositoryImpl() }
}