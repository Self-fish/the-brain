import application.usb.UsbController
import application.logger.LoggerWrapper
import org.koin.standalone.KoinComponent

open class BaseAction(private val logger: LoggerWrapper,
                      private val usbController: UsbController) : KoinComponent {

    fun executeAction(command: String) : String {
        logger.info(this::class.simpleName, "Input: $command")
        usbController.sendCommand(command)
        val commandRead = usbController.readCommand()
        logger.info(this::class.simpleName, "Output: $commandRead")
        return commandRead
    }
}