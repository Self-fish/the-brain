package application

import application.usb.UsbController
import application.usb.UsbControllerMockImpl
import application.logger.LoggerWrapper
import application.usb.UsbControllerImpl
import org.koin.dsl.module.module
import java.util.logging.Logger

val applicationModule = module {
    single { LoggerWrapper( Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)) }
    //single<UsbController> { UsbControllerImpl.newInstance() }
    single<UsbController> { UsbControllerMockImpl() }
}