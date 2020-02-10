package waterTemperatureFeature

import org.koin.dsl.module.module
import waterTemperatureFeature.action.UpdateWaterTemperatureActionImpl
import waterTemperatureFeature.domain.UpdateWaterTemperatureUseCase
import waterTemperatureFeature.domain.contract.UpdateWaterTemperatureAction

val waterTemperatureModule = module {
    single { UpdateWaterTemperatureUseCase(get()) }
    single<UpdateWaterTemperatureAction> { UpdateWaterTemperatureActionImpl(get(), get()) }
}