package handleLightsFeature

import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin
import handleLightsFeature.data.controller.LightsController
import handleLightsFeature.data.datasource.LightPreferencesLocalDataSource
import handleLightsFeature.data.repository.LightPreferencesRepositoryImpl
import handleLightsFeature.data.repository.LightStatusRepositoryImpl
import handleLightsFeature.domain.HandleLightsUseCase
import handleLightsFeature.domain.contracts.repository.LightPreferencesRepository
import handleLightsFeature.domain.contracts.repository.LightStatusRepository
import org.koin.dsl.module.module

val lightModule = module {
    single { LightsController(initialiseRelay()) }
    single { HandleLightsUseCase(get(), get(), get()) }
    single<LightStatusRepository> { LightStatusRepositoryImpl(get(), get()) }
    single<LightPreferencesRepository> { LightPreferencesRepositoryImpl(get()) }
    single { LightPreferencesLocalDataSource() }

}

fun initialiseRelay(): GpioPinDigitalOutput =
        GpioFactory.getInstance().provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.HIGH)