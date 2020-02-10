package boxHumidityFeature

import boxHumidityFeature.action.UpdateBoxHumidityActionImpl
import boxHumidityFeature.domain.UpdateBoxHumidityUseCase
import boxHumidityFeature.domain.contract.UpdateBoxHumidityAction
import org.koin.dsl.module.module

val boxHumidityModule = module {
    single { UpdateBoxHumidityUseCase(get()) }
    single<UpdateBoxHumidityAction> { UpdateBoxHumidityActionImpl(get(), get()) }
}