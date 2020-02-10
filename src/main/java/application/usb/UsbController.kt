package application.usb

import javax.usb.UsbException

interface UsbController {

    @Throws(UsbException::class)
    fun sendCommand(command: String)
    @Throws(UsbException::class, InterruptedException::class)
    fun readCommand() : String

    fun eraseResource()

}