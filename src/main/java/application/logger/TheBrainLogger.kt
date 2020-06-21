package application.logger
import LoggerFormatter
import java.io.File
import java.util.logging.ConsoleHandler
import java.util.logging.FileHandler
import java.util.logging.Level
import java.util.logging.Logger

class TheBrainLogger {

    companion object {
        const val LOG_FILE = "Logs.html"
    }


    fun setUp() {

        val logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)
        val rootLogger = Logger.getLogger("")
        val handlers = rootLogger.handlers
        createLogsFile()
        val fileHtml = FileHandler(LOG_FILE)

        if (handlers[0] is ConsoleHandler) {
            rootLogger.removeHandler(handlers[0])
        }

        logger.level = Level.ALL
        fileHtml.formatter = LoggerFormatter()
        logger.addHandler(fileHtml)
    }

    fun createLogsFile() {
        val logsFile = File(LOG_FILE)
        logsFile.delete()
        logsFile.createNewFile()
        logsFile.setReadable(true, false)
        logsFile.setExecutable(true, false)
        logsFile.setWritable(true, false)
    }




}