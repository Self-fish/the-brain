package showBoxTemperatureFeature.action

import BaseAction
import application.usb.UsbController
import application.logger.LoggerWrapper
import showBoxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction
import showBoxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction.Companion.BOX_TEMP_SCREEN_UPDATE
import org.koin.standalone.KoinComponent
import showBoxTemperatureFeature.domain.contract.UpdateBoxTemperatureAction.Companion.OK

open class UpdateBoxTemperatureActionImpl(logger: LoggerWrapper, usbController: UsbController) :
        KoinComponent, BaseAction(logger, usbController), UpdateBoxTemperatureAction {

    override fun updateBoxTemperature() = executeAction(BOX_TEMP_SCREEN_UPDATE) == OK

}