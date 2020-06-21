package configurationFeature.data.action

import BaseAction
import application.logger.LoggerWrapper
import application.usb.UsbController
import org.koin.standalone.KoinComponent

class UpdateConfigurationAction(logger: LoggerWrapper, usbController: UsbController) : KoinComponent,
        BaseAction(logger, usbController) {

    companion object {
        private const val ACTION = "CON"
        private const val OK = "OK"
    }

    fun sendCurrentTime(currentTime: Long) : Boolean {
        return executeAction("$ACTION:$currentTime") == OK
    }

}