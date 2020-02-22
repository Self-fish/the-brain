package showWaterTempFeature.action

import BaseAction
import application.usb.UsbController
import application.logger.LoggerWrapper
import org.koin.standalone.KoinComponent
import showWaterTempFeature.domain.contract.UpdateWaterTemperatureAction
import showWaterTempFeature.domain.contract.UpdateWaterTemperatureAction.Companion.CACHE_TTL
import showWaterTempFeature.domain.contract.UpdateWaterTemperatureAction.Companion.WATER_TEMP_SCREEN_UPDATE

open class UpdateWaterTemperatureActionImpl(logger: LoggerWrapper, usbController: UsbController) :
        KoinComponent, BaseAction(logger, usbController), UpdateWaterTemperatureAction {

    var lastUpdate = 0L

    override fun updateWaterTemperature() {
        val currentTime = System.currentTimeMillis()
        if(currentTime > CACHE_TTL + lastUpdate) {
            lastUpdate = currentTime
            executeAction(WATER_TEMP_SCREEN_UPDATE)
        }
    }
}