package showBoxHumidityFeature.action

import BaseAction
import application.usb.UsbController
import application.logger.LoggerWrapper
import showBoxHumidityFeature.domain.contract.UpdateBoxHumidityAction
import showBoxHumidityFeature.domain.contract.UpdateBoxHumidityAction.Companion.HUMIDITY_SCREEN_UPDATE
import org.koin.standalone.KoinComponent
import showBoxHumidityFeature.domain.contract.UpdateBoxHumidityAction.Companion.OK

open class UpdateBoxHumidityActionImpl(logger: LoggerWrapper, usbController: UsbController) :
        KoinComponent, BaseAction(logger, usbController), UpdateBoxHumidityAction {

    override fun updateBoxHumidity() = executeAction(HUMIDITY_SCREEN_UPDATE) == OK

}