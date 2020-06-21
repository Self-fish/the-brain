package application

import application.logger.TheBrainLogger
import application.usb.UsbController
import showBoxHumidityFeature.boxHumidityModule
import showBoxHumidityFeature.domain.UpdateBoxHumidityUseCase
import showBoxTemperatureFeature.domain.UpdateBoxTemperatureUseCase
import showBoxTemperatureFeature.boxTemperatureModule
import configurationFeature.configurationModule
import configurationFeature.domain.UpdateConfigurationUseCase
import handleAlertsFeature.alertsModule
import handleAlertsFeature.domain.HandleAlertsUseCase
import handleLightsFeature.domain.HandleLightsUseCase
import handleLightsFeature.lightModule
import handleWaterTempFeature.domain.HandleWaterTempUseCase
import handleWaterTempFeature.handleWaterTempModule
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import showWaterTempFeature.domain.UpdateWaterTemperatureUseCase
import showWaterTempFeature.waterTemperatureModule
import java.lang.Thread.sleep

class MainApplication : KoinComponent {

    private val updateBoxTemperature: UpdateBoxTemperatureUseCase by inject()
    private val updateBoxHumidity: UpdateBoxHumidityUseCase by inject()
    private val updateWaterTemperature: UpdateWaterTemperatureUseCase by inject()
    private val handleLight : HandleLightsUseCase by inject()
    private val updateConfiguration: UpdateConfigurationUseCase by inject()
    private val usbController: UsbController by inject()
    private val handleWaterTemp: HandleWaterTempUseCase by inject()
    private val handleAlerts: HandleAlertsUseCase by inject()

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

    fun handleWaterTemp() {
        handleWaterTemp.handleWaterTemp()
    }

    fun handleAlerts() {
        handleAlerts.handleAlerts()
    }

}

fun main(args: Array<String>) {

    StandAloneContext.startKoin(listOf(boxTemperatureModule, applicationModule, lightModule, boxHumidityModule,
            waterTemperatureModule, configurationModule, handleWaterTempModule, alertsModule))
    TheBrainLogger().setUp()

    while (true) {
        MainApplication().updateConfiguration()
        MainApplication().boxTemperatureUpdate()
        MainApplication().boxHumidityUpdate()
        MainApplication().waterTemperatureUpdate()
        MainApplication().handleLights()
        MainApplication().handleAlerts()
        sleep(1000)
    }

}
