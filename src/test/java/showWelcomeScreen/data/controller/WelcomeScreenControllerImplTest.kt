package showWelcomeScreen.data.controller

import application.controller.LCDController
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito

internal class WelcomeScreenControllerImplTest {

    companion object {
        private const val NAME = "Pablo"
        private const val WELCOME_MESSAGE = "Welcome"
        private const val WAIT_MESSAGE = "Please wait..."
        private val customChars = arrayOf(
                byteArrayOf(0x0F, 0x18, 0x13, 0x17, 0x17, 0x13, 0x18, 0x0F),
                byteArrayOf(0x1F, 0x00, 0x1F, 0x1F, 0x1F, 0x1F, 0x00, 0x1F),
                byteArrayOf(0x1F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1F),
                byteArrayOf(0x1E, 0x03, 0x01, 0x01, 0x01, 0x01, 0x03, 0x1E),
                byteArrayOf(0x1E, 0x03, 0x19, 0x1D, 0x1D, 0x19, 0x02, 0x1E),
                byteArrayOf(0x00, 0x00, 0x07, 0x0F, 0x1B, 0x0F, 0x07, 0x00),
                byteArrayOf(0x00, 0x01, 0x11, 0x1B, 0x1F, 0x1B, 0x11, 0x01)
        )
    }

    private val lcdController = Mockito.mock(LCDController::class.java)
    private val screenController = WelcomeScreenControllerImpl(lcdController)

    @Test
    @DisplayName("the welcome message is paint correctly")
    fun showWelcomeMessageCorrectly() {
        screenController.showWelcomeMessage(NAME)
        Mockito.verify(lcdController, Mockito.times(1)).
            writeString("$WELCOME_MESSAGE $NAME!", 1, 3)
        Mockito.verify(lcdController, Mockito.times(1)).
            writeString(WAIT_MESSAGE, 2, 3)
    }

    @Test
    @DisplayName("We call the controller to clean the screen")
    fun cleanScreen() {
        screenController.cleanScreen()
        Mockito.verify(lcdController, Mockito.times(1)).clear()
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }


}