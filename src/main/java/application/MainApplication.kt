package application

import application.logger.TheBrainLogger
import handleLightsFeature.domain.HandleLightsUseCase
import handleLightsFeature.lightModule
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import showBoxHumidityFeature.domain.UpdateBoxHumidityUseCase
import showWelcomeScreen.domain.WelcomeScreenUseCase
import showWelcomeScreen.welcomeScreenModule
import collectHumidityFeature
import collectHumidityFeature.domain.CollectHumidityUseCase
import showBoxHumidityFeature.boxHumidityModule
import java.util.*
import kotlin.concurrent.schedule

class MainApplication : KoinComponent {

    private val handleLight : HandleLightsUseCase by inject()
    private val welcomeScreen: WelcomeScreenUseCase by inject()
    private val collectHumidity: CollectHumidityUseCase by inject()
    private val humidityUseCase: UpdateBoxHumidityUseCase by inject()


    fun handleLights() {
        Timer("HandleLightsTask", false).schedule(0, 10000) {
            handleLight.handleLights()
        }
    }

    fun collectHumidityAndTemperature() {
        Timer("CollectHumidityAndTemperatureTask", false).schedule(0, 30000) {
            collectHumidity.collectTemperatureAndHumidity()
        }
    }

    fun startApplication() {
        welcomeScreen.showScreen()
    }

    fun printCarousel() {
        while(true) {
            humidityUseCase.updateBoxHumidity()
            Thread.sleep(3000)
        }
    }

}

fun main(args: Array<String>) {

    StandAloneContext.startKoin(listOf(applicationModule, lightModule, welcomeScreenModule, collectHumidityFeature,
            boxHumidityModule))
    TheBrainLogger().setUp()

    MainApplication().startApplication()
    MainApplication().collectHumidityAndTemperature()
    MainApplication().handleLights()
    MainApplication().printCarousel()




}
