package application.logger
import org.koin.standalone.KoinComponent
import java.util.logging.Logger

open class LoggerWrapper(private val logger: Logger) : KoinComponent {

    fun info(className: String?, message: String) {
        logger.info("$className: $message")
        println("$className: $message")
    }

    fun warning(className: String?, message: String) {
        logger.warning("$className: $message")
        println("$className: $message")
    }

    fun finest(className: String?, message: String) {
        logger.finest("$className: $message")
        println("$className: $message")
    }

    fun fine(className: String?, message: String) {
        logger.fine("$className: $message")
        println("$className: $message")
    }

    fun severe(className: String?, message: String) {
        logger.severe("$className: $message")
        println("$className: $message")
    }
}