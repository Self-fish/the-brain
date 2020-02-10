package configurationFeature.data.action

import BaseAction
import application.logger.LoggerWrapper
import application.usb.UsbController
import configurationFeature.domain.contracts.action.UpdateConfigurationAction
import configurationFeature.domain.contracts.action.UpdateConfigurationAction.Companion.ACTION
import configurationFeature.domain.contracts.action.UpdateConfigurationAction.Companion.CACHE_TTL
import org.koin.standalone.KoinComponent

class UpdateConfigurationActionImpl(logger: LoggerWrapper, usbController: UsbController) : KoinComponent,
        BaseAction(logger, usbController), UpdateConfigurationAction {

    var lastUpdate = 0L

    override fun updateConfiguration(currentTime: Long) {
        if(currentTime > CACHE_TTL + lastUpdate) {
            lastUpdate = currentTime
            executeAction("$ACTION:$currentTime");
        }
    }

}