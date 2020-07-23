package showWelcomeScreen.data.controller

import application.controller.LCDController
import showWelcomeScreen.domain.contracts.WelcomeScreenController

class WelcomeScreenControllerImpl(private val lcdController: LCDController): WelcomeScreenController {

    companion object {
        private val customChars = arrayOf(
                byteArrayOf(0x0F, 0x18, 0x13, 0x17, 0x17, 0x13, 0x18, 0x0F),
                byteArrayOf(0x1F, 0x00, 0x1F, 0x1F, 0x1F, 0x1F, 0x00, 0x1F),
                byteArrayOf(0x1F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F),
                byteArrayOf(0x1E, 0x03, 0x01, 0x01, 0x01, 0x01, 0x03, 0x1E),
                byteArrayOf(0x1E, 0x03, 0x19, 0x1D, 0x1D, 0x19, 0x02, 0x1E),
                byteArrayOf(0x00, 0x00, 0x07, 0x0F, 0x1B, 0x0F, 0x07, 0x00),
                byteArrayOf(0x00, 0x01, 0x11, 0x1B, 0x1F, 0x1B, 0x11, 0x01)
        )
        private const val LOADING_BAR_1 = 0x00.toByte()
        private const val LOADING_BAR_2 = 0x01.toByte()
        private const val LOADING_BAR_3 = 0x02.toByte()
        private const val LOADING_BAR_4 = 0x03.toByte()
        private const val LOADING_BAR_5 = 0x04.toByte()
        private const val FISH_1 = 0x05.toByte()
        private const val FISH_2 = 0x06.toByte()
    }

    override fun showWelcomeMessage(name: String) {
        initialise()
        lcdController.writeString("Welcome $name!", 1, 3)
        lcdController.writeString("Please wait...", 2, 3)
    }

    override fun buildAnimation(duration: Int) {
        lcdController.clear()
        lcdController.loadCustomChars(customChars)

        for (i in 0..9) {
            paintLoadingBar(i)
            lcdController.clearLine(0)
            lcdController.clearLine(1)
            paintFishBank(3 - i)
            Thread.sleep(200)
        }
    }

    override fun cleanScreen() {
        lcdController.clear()
    }

    private fun paintLoadingBar(progress: Int) {
        lcdController.setCursor(3, 2)
        lcdController.writeCharacter(LOADING_BAR_1)
        for (i in 1..9) {
            if (i != 9 && i <= progress) {
                lcdController.writeCharacter(LOADING_BAR_2)
            } else if (i != 9 && i > progress) {
                lcdController.writeCharacter(LOADING_BAR_3)
            } else if (i == 9 && progress == 9) {
                lcdController.writeCharacter(LOADING_BAR_5)
            } else {
                lcdController.writeCharacter(LOADING_BAR_4)
            }
        }
        paintProgress(progress)
    }

    private fun paintProgress(progress: Int) {
        if(progress == 9) {
            lcdController.writeString("${(progress + 1) * 10}%", 3, 14)
        } else {
            lcdController.writeString("${(progress + 1) * 10}%", 3, 15)
        }
    }

    private fun paintFishBank(startingColumn: Int) {
        paintFish(0, startingColumn)
        paintFish(0, startingColumn + 5)
        paintFish(0, startingColumn + 11)
        paintFish(0, startingColumn + 15)
        paintFish(1, startingColumn - 2)
        paintFish(1, startingColumn + 3)
        paintFish(1, startingColumn + 9)
        paintFish(1, startingColumn + 13)
    }

    private fun paintFish(line: Int, column: Int) {
        when {
            column >= 0 -> {
                lcdController.setCursor(line, column)
                lcdController.writeCharacter(FISH_1)
                lcdController.writeCharacter(FISH_2)
            }
            column == -1 -> {
                lcdController.setCursor(line, column + 20)
                lcdController.writeCharacter(FISH_1)
                lcdController.setCursor(line, column + 1)
                lcdController.writeCharacter(FISH_2)
            }
            else -> {
                lcdController.setCursor(line, column + 20)
                lcdController.writeCharacter(FISH_1)
                lcdController.setCursor(line, column + 21)
                lcdController.writeCharacter(FISH_2)
            }
        }
    }

    private fun initialise() {
        lcdController.init()
        lcdController.backLight(true)
    }

}