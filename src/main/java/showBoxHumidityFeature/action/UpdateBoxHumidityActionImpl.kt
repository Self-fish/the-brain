package showBoxHumidityFeature.action

import BaseAction
import application.usb.UsbController
import application.logger.LoggerWrapper
import showBoxHumidityFeature.domain.contract.UpdateBoxHumidityAction
import showBoxHumidityFeature.domain.contract.UpdateBoxHumidityAction.Companion.CACHE_TTL
import showBoxHumidityFeature.domain.contract.UpdateBoxHumidityAction.Companion.HUMIDITY_SCREEN_UPDATE
import org.koin.standalone.KoinComponent

open class UpdateBoxHumidityActionImpl(logger: LoggerWrapper, usbController: UsbController) :
        KoinComponent, BaseAction(logger, usbController), UpdateBoxHumidityAction {

    var lastUpdate = 0L

    override fun updateBoxHumidity() {
        val currentTime = System.currentTimeMillis()
        if(currentTime > CACHE_TTL + lastUpdate) {
            lastUpdate = currentTime
            executeAction(HUMIDITY_SCREEN_UPDATE)
        }
    }

}