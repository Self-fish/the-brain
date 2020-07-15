package application

import application.logger.TheBrainLogger
import showBoxHumidityFeature.boxHumidityModule
import showBoxTemperatureFeature.boxTemperatureModule
import configurationFeature.configurationModule
import handleLightsFeature.domain.HandleLightsUseCase
import handleLightsFeature.lightModule
import handleWaterTempFeature.handleWaterTempModule
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import showWaterTempFeature.waterTemperatureModule
import java.util.*
import kotlin.concurrent.schedule

class MainApplication : KoinComponent {

    private val handleLight : HandleLightsUseCase by inject()

    fun handleLights() {
        Timer("HandleLightsTask", false).schedule(0, 10000) {
            handleLight.handleLights()
        }
    }

}

fun main(args: Array<String>) {

    StandAloneContext.startKoin(listOf(boxTemperatureModule, applicationModule, lightModule, boxHumidityModule,
            waterTemperatureModule, configurationModule, handleWaterTempModule))
    TheBrainLogger().setUp()

    MainApplication().handleLights()


}
