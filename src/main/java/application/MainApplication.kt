package application

import application.logger.TheBrainLogger
import handleLightsFeature.domain.HandleLightsUseCase
import handleLightsFeature.lightModule
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import showBoxHumidityFeature.boxHumidityModule
import showBoxHumidityFeature.domain.UpdateBoxHumidityUseCase
import showWelcomeScreen.domain.WelcomeScreenUseCase
import showWelcomeScreen.welcomeScreenModule
import java.util.*
import kotlin.concurrent.schedule

class MainApplication : KoinComponent {

    private val handleLight : HandleLightsUseCase by inject()
    private val welcomeScreen: WelcomeScreenUseCase by inject()
    private val boxHUmidity: UpdateBoxHumidityUseCase by inject()


    fun handleLights() {
        Timer("HandleLightsTask", false).schedule(0, 10000) {
            handleLight.handleLights()
        }
    }

    fun getHumidity() {
        boxHUmidity.updateBoxHumidity()
    }

    fun startApplication() {
        welcomeScreen.showScreen()
    }

}

fun main(args: Array<String>) {

    StandAloneContext.startKoin(listOf(applicationModule, lightModule, welcomeScreenModule, boxHumidityModule))
    TheBrainLogger().setUp()

    MainApplication().startApplication()
    //MainApplication().handleLights()
    MainApplication().getHumidity()



}
