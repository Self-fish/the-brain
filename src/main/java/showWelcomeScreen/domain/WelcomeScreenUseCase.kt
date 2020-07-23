package showWelcomeScreen.domain

import showWelcomeScreen.domain.contracts.WelcomeScreenController

class WelcomeScreenUseCase(private val screenController: WelcomeScreenController) {

    fun showScreen() {
        screenController.showWelcomeMessage("Pablo")
        Thread.sleep(3000L)
        screenController.buildAnimation(30)
        Thread.sleep(1000L)
        screenController.cleanScreen()
    }

}