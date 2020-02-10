package application.logger
import org.koin.standalone.KoinComponent
import java.util.logging.Logger

open class LoggerWrapper(private val logger: Logger) : KoinComponent {

    fun info(className: String?, message: String) {
        logger.info("$className: $message")
        println("$className: $message")
    }

}