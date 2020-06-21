package showBoxHumidityFeature

import showBoxHumidityFeature.action.UpdateBoxHumidityActionImpl
import showBoxHumidityFeature.domain.UpdateBoxHumidityUseCase
import showBoxHumidityFeature.domain.contract.UpdateBoxHumidityAction
import org.koin.dsl.module.module

val boxHumidityModule = module {
    single { UpdateBoxHumidityUseCase(get(), get()) }
    single<UpdateBoxHumidityAction> { UpdateBoxHumidityActionImpl(get(), get()) }
}