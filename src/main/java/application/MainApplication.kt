package application

import application.logger.TheBrainLogger
import application.usb.UsbController
import boxHumidityFeature.boxHumidityModule
import boxHumidityFeature.domain.UpdateBoxHumidityUseCase
import boxTemperatureFeature.domain.UpdateBoxTemperatureUseCase
import boxTemperatureFeature.boxTemperatureModule
import configurationFeature.configurationModule
import configurationFeature.domain.UpdateConfigurationUseCase
import lightsFeature.domain.HandleLightsUseCase
import lightsFeature.lightModule
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import waterTemperatureFeature.domain.UpdateWaterTemperatureUseCase
import waterTemperatureFeature.waterTemperatureModule
import java.lang.Thread.sleep

class MainApplication : KoinComponent {

    private val updateBoxTemperature: UpdateBoxTemperatureUseCase by inject()
    private val updateBoxHumidity: UpdateBoxHumidityUseCase by inject()
    private val updateWaterTemperature: UpdateWaterTemperatureUseCase by inject()
    private val handleLight : HandleLightsUseCase by inject()
    private val updateConfiguration: UpdateConfigurationUseCase by inject()
    private val usbController: UsbController by inject()

    fun boxTemperatureUpdate() {
        updateBoxTemperature.updateBoxTemperature()
    }

    fun boxHumidityUpdate() {
        updateBoxHumidity.updateBoxHumidity()
    }

    fun waterTemperatureUpdate() {
        updateWaterTemperature.updateWaterTemperature()
    }

    fun updateConfiguration() {
        updateConfiguration.updateConfiguration()
    }

    fun eraseResource() {
        usbController.eraseResource()
    }

    fun handleLights() {
        handleLight.handleLights()
    }

}

fun main(args: Array<String>) {

    StandAloneContext.startKoin(listOf(boxTemperatureModule, applicationModule, lightModule, boxHumidityModule,
            waterTemperatureModule, configurationModule))
    TheBrainLogger().setUp()

    MainApplication().updateConfiguration()
    MainApplication().boxTemperatureUpdate()
    MainApplication().boxHumidityUpdate()
    MainApplication().waterTemperatureUpdate()
    MainApplication().eraseResource()


    while (true) {
        /*MainApplication().updateConfiguration()
        MainApplication().boxTemperatureUpdate()
        MainApplication().boxHumidityUpdate()
        MainApplication().waterTemperatureUpdate()*/
        sleep(1000)
    }

}
