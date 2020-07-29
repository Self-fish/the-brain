package showBoxTemperatureFeature

import showBoxTemperatureFeature.domain.UpdateBoxTemperatureUseCase
import org.koin.dsl.module.module
import showBoxTemperatureFeature.data.controller.ScreenControllerImpl
import showBoxTemperatureFeature.domain.contract.ScreenController
import showWelcomeScreen.initialiseLCD

val boxTemperatureModule = module {
    single { UpdateBoxTemperatureUseCase(get(), get(), get()) }
    single<ScreenController> { ScreenControllerImpl(initialiseLCD()) }
}