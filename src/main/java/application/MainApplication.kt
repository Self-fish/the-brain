package application

import application.logger.TheBrainLogger
import handleLightsFeature.domain.HandleLightsUseCase
import handleLightsFeature.lightModule
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import showWelcomeScreen.domain.WelcomeScreenUseCase
import showWelcomeScreen.welcomeScreenModule
import java.util.*
import kotlin.concurrent.schedule

class MainApplication : KoinComponent {

    private val handleLight : HandleLightsUseCase by inject()
    private val welcomeScreen: WelcomeScreenUseCase by inject()


    fun handleLights() {
        Timer("HandleLightsTask", false).schedule(0, 10000) {
            handleLight.handleLights()
        }
    }

    fun startApplication() {
        welcomeScreen.showScreen()
    }

}

fun main(args: Array<String>) {

    StandAloneContext.startKoin(listOf(applicationModule, lightModule, welcomeScreenModule))
    TheBrainLogger().setUp()

    MainApplication().startApplication()
    MainApplication().handleLights()



}
