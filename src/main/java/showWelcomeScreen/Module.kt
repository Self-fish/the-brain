package showWelcomeScreen

import application.controller.LCDController
import com.pi4j.io.i2c.I2CBus
import com.pi4j.io.i2c.I2CFactory
import org.koin.dsl.module.module
import showWelcomeScreen.data.controller.WelcomeScreenControllerImpl
import showWelcomeScreen.domain.WelcomeScreenUseCase
import showWelcomeScreen.domain.contracts.WelcomeScreenController

val welcomeScreenModule = module {
    single { WelcomeScreenUseCase(get()) }
    single<WelcomeScreenController> { WelcomeScreenControllerImpl(initialiseLCD()) }
}

fun initialiseLCD(): LCDController = LCDController(I2CFactory.getInstance(I2CBus.BUS_1).getDevice(0x27))