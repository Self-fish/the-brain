package boxTemperatureFeature

import boxTemperatureFeature.action.UpdateBoxTemperatureActionImpl
import boxTemperatureFeature.domain.UpdateBoxTemperatureUseCase
import boxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction
import org.koin.dsl.module.module

val boxTemperatureModule = module {
    single { UpdateBoxTemperatureUseCase(get()) }
    single<UpdateBoxTemperatureAction> { UpdateBoxTemperatureActionImpl(get(), get()) }
}