package showBoxTemperatureFeature.data.controller

import application.controller.LCDController
import showBoxTemperatureFeature.domain.contract.ScreenController

class ScreenControllerImpl(private val lcdController: LCDController): ScreenController {

    companion object {
        private val customChars = arrayOf(
                byteArrayOf(0x00, 0x00, 0x04, 0x0E, 0x0A, 0x0E, 0x04, 0x04),
                byteArrayOf(0x00, 0x00, 0x1F, 0x00, 0x00, 0x00, 0x00, 0x00),
                byteArrayOf(0x04, 0x04, 0x1F, 0x04, 0x04, 0x04, 0x04, 0x04),
                byteArrayOf(0x04, 0x04, 0x04, 0x04, 0x04, 0x04, 0x04, 0x1F),
                byteArrayOf(0x00, 0x00, 0x1E, 0x1C, 0x1C, 0x12, 0x01, 0x00),
                byteArrayOf(0x00, 0x00, 0x0F, 0x07, 0x07, 0x09, 0x10, 0x00),
                byteArrayOf(0x04, 0x0A, 0x0A, 0x0E, 0x0E, 0x1F, 0x1F, 0x0E)
        )

        private const val ANCHOR_1 = 0x00.toByte()
        private const val ANCHOR_2 = 0x01.toByte()
        private const val ANCHOR_3 = 0x02.toByte()
        private const val ANCHOR_4 = 0x03.toByte()
        private const val ANCHOR_5 = 0x04.toByte()
        private const val ANCHOR_6 = 0x05.toByte()
        private const val TEMPERATURE_ICON = 0x06.toByte()
    }

    override fun printBoxTemperature(temperature: Double, date: String): Boolean {
        paintAnchor()
        paintHour(date)
        paintTemperature(temperature)
        return true
    }

    private fun paintAnchor() {
        lcdController.loadCustomChars(customChars)
        lcdController.setCursor(1, 3)
        lcdController.writeCharacter(ANCHOR_1)
        lcdController.setCursor(2, 2)
        lcdController.writeCharacter(ANCHOR_2)
        lcdController.setCursor(2, 4)
        lcdController.writeCharacter(ANCHOR_2)
        lcdController.setCursor(2, 3)
        lcdController.writeCharacter(ANCHOR_3)
        lcdController.setCursor(3, 3)
        lcdController.writeCharacter(ANCHOR_4)
        lcdController.setCursor(3, 2)
        lcdController.writeCharacter(ANCHOR_5)
        lcdController.setCursor(3, 4)
        lcdController.writeCharacter(ANCHOR_6)
    }

    private fun paintHour(date: String) {
        lcdController.writeString(date, 0, 1)
    }

    private fun paintTemperature(temperature: Double) {
        lcdController.setCursor(2, 9)
        lcdController.writeCharacter(TEMPERATURE_ICON)
        lcdController.writeString("= ${temperature}C", 2, 11)
    }

}