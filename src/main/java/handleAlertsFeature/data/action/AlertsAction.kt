package handleAlertsFeature.data.action

import BaseAction
import application.logger.LoggerWrapper
import application.usb.UsbController
import handleAlertsFeature.domain.`model `.Alert
import org.koin.standalone.KoinComponent

open class AlertsAction (logger: LoggerWrapper, usbController: UsbController) :
        BaseAction(logger, usbController), KoinComponent {

    private val SEND_ALERT = "S_A"

    enum class AlertActionResponse {
        OK, LAT
    }

    fun sendAlert(alert : Alert): AlertActionResponse {
        return AlertActionResponse.valueOf(executeAction("$SEND_ALERT:${alert.text}"))
    }

}