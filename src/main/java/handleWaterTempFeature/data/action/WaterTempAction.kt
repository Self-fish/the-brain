package handleWaterTempFeature.data.action

import BaseAction
import application.logger.LoggerWrapper
import application.usb.UsbController
import org.koin.standalone.KoinComponent

class WaterTempAction(logger: LoggerWrapper, usbController: UsbController) :
        KoinComponent, BaseAction(logger, usbController) {

    private val HEATER_ON = "T_N"
    private val HEATER_OFF = "T_F"
    private val GET_WATER_TEMP = "T_G"


    fun turnOnHeater() : Boolean {
        if(executeAction(HEATER_ON) == "OK") {
            return true
        }
        return false
    }


    fun turnOffHeater() : Boolean {
        if(executeAction(HEATER_OFF) == "OK") {
            return true
        }
        return false
    }

    fun getWaterTemperature() : String {
        return executeAction(GET_WATER_TEMP)
    }


}