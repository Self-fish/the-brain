package showWelcomeScreen.domain.contracts

interface WelcomeScreenController {

    fun showWelcomeMessage(name: String)
    fun buildAnimation(duration: Int)
    fun cleanScreen()

}