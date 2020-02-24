package showWaterTempFeature

import org.koin.dsl.module.module
import showWaterTempFeature.action.UpdateWaterTemperatureActionImpl
import showWaterTempFeature.domain.UpdateWaterTemperatureUseCase
import showWaterTempFeature.domain.contract.UpdateWaterTemperatureAction

val waterTemperatureModule = module {
    single { UpdateWaterTemperatureUseCase(get()) }
    single<UpdateWaterTemperatureAction> { UpdateWaterTemperatureActionImpl(get(), get()) }
}