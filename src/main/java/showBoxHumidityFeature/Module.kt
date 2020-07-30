package showBoxHumidityFeature

import org.koin.dsl.module.module
import showBoxHumidityFeature.data.controller.ScreenControllerImpl
import showBoxHumidityFeature.domain.UpdateBoxHumidityUseCase
import showBoxHumidityFeature.domain.contract.controller.ScreenController
import showWelcomeScreen.initialiseLCD

val boxHumidityModule = module {
    single<ScreenController> { ScreenControllerImpl(initialiseLCD()) }
    single { UpdateBoxHumidityUseCase(get(), get(), get()) }
}