package application.usb

class UsbControllerMockImpl : UsbController {

    private var answer = "KO"

    override fun sendCommand(command: String) {
        answer = when(command) {
            "T_U" -> "OK"
            "H_U" -> "OK"
            "W_U" -> "OK"
            "L_N" -> "OK"
            "L_F" -> "OK"
            "L_G" -> "ON"
            "T_N" -> "OK"
            "T_F" -> "OK"
            "T_G" -> "27.0"
            "S_A" -> "OK"
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