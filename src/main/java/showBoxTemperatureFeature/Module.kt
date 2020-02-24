package showBoxTemperatureFeature

import showBoxTemperatureFeature.action.UpdateBoxTemperatureActionImpl
import showBoxTemperatureFeature.domain.UpdateBoxTemperatureUseCase
import showBoxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction
import org.koin.dsl.module.module

val boxTemperatureModule = module {
    single { UpdateBoxTemperatureUseCase(get()) }
    single<UpdateBoxTemperatureAction> { UpdateBoxTemperatureActionImpl(get(), get()) }
}