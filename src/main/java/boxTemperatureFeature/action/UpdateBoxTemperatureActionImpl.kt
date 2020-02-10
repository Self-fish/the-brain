package boxTemperatureFeature.action

import BaseAction
import application.usb.UsbController
import application.logger.LoggerWrapper
import boxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction
import boxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction.Companion.BOX_TEMP_SCREEN_UPDATE
import boxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction.Companion.CACHE_TTL
import org.koin.standalone.KoinComponent

open class UpdateBoxTemperatureActionImpl(logger: LoggerWrapper, usbController: UsbController) :
        KoinComponent, BaseAction(logger, usbController), UpdateBoxTemperatureAction {

    var lastUpdate = 0L

    override fun updateBoxTemperature() {
        val currentTime = System.currentTimeMillis()
        if(currentTime > CACHE_TTL + lastUpdate) {
            lastUpdate = currentTime
            executeAction(BOX_TEMP_SCREEN_UPDATE)
        }
    }

}