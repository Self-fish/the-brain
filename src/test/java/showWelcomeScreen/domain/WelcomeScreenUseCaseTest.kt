package showWelcomeScreen.domain

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import showWelcomeScreen.domain.contracts.WelcomeScreenController

internal class WelcomeScreenUseCaseTest {

    private val screenController = Mockito.mock(WelcomeScreenController::class.java)
    private val useCase = WelcomeScreenUseCase(screenController)

    @Test
    @DisplayName("We create all the elements in the screen")
    fun showScreen() {
        useCase.showScreen()
        Mockito.verify(screenController, Mockito.times(1)).showWelcomeMessage("Pablo")
        Mockito.verify(screenController, Mockito.times(1)).buildAnimation(30)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}