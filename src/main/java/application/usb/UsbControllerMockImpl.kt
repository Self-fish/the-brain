package application.usb

class UsbControllerMockImpl : UsbController {

    private var answer = "KO"

    override fun sendCommand(command: String) {
        answer = when(command) {
            "T_U" -> "OK"
            "H_U" -> "OK"
            "W_U" -> "OK"
            "L_ON" -> "OK"
            "L_OFF" -> "OK"
            "L_G" -> "ON"
            else -> "KO"
        }
    }

    override fun readCommand(): String {
        return answer
    }

    override fun eraseResource() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}