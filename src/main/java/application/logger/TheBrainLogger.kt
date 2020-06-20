package application.logger
import LoggerFormatter
import java.util.logging.ConsoleHandler
import java.util.logging.FileHandler
import java.util.logging.Level
import java.util.logging.Logger

class TheBrainLogger {

    fun setUp() {

        val logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)
        val rootLogger = Logger.getLogger("")
        val handlers = rootLogger.handlers
        val fileHtml = FileHandler(LOG_FILE)

        if (handlers[0] is ConsoleHandler) {
            rootLogger.removeHandler(handlers[0])
        }

        logger.level = Level.ALL
        fileHtml.formatter = LoggerFormatter()
        logger.addHandler(fileHtml)
    }

    companion object {
        const val LOG_FILE = "Logs.html"
    }


}