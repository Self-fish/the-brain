package showWaterTempFeature.action

import BaseAction
import application.usb.UsbController
import application.logger.LoggerWrapper
import org.koin.standalone.KoinComponent
import showWaterTempFeature.domain.contract.UpdateWaterTemperatureAction
import showWaterTempFeature.domain.contract.UpdateWaterTemperatureAction.Companion.OK
import showWaterTempFeature.domain.contract.UpdateWaterTemperatureAction.Companion.WATER_TEMP_SCREEN_UPDATE

open class UpdateWaterTemperatureActionImpl(logger: LoggerWrapper, usbController: UsbController) :
        KoinComponent, BaseAction(logger, usbController), UpdateWaterTemperatureAction {


    override fun updateWaterTemperature() = executeAction(WATER_TEMP_SCREEN_UPDATE) == OK

}