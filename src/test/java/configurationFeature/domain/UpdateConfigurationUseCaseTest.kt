package configurationFeature.domain

import application.logger.LoggerWrapper
import configurationFeature.domain.contracts.repository.ConfigurationRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import java.util.logging.Logger

internal class UpdateConfigurationUseCaseTest {

    companion object {
        const val SUCCESS_LOG = "Time updated successfully"
        const val ERROR_LOG = "Something went wrong updating the time"
    }

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val repository = Mockito.mock(ConfigurationRepository::class.java)
    private val useCase = UpdateConfigurationUseCase(repository, loggerWrapper)

    @Test
    @DisplayName("When the action is successful we log the success message")
    fun executeUpdateConfigurationSuccess() {
        Mockito.`when`(repository.sendCurrentTime()).thenReturn(true)
        useCase.updateConfiguration()
        Mockito.verify(repository, Mockito.times(1)).sendCurrentTime()
        val classname = useCase::class.simpleName
        Mockito.verify(logger, Mockito.times(1)).info("$classname: $SUCCESS_LOG")
    }

    @Test
    @DisplayName("When the action failed we log the failure message")
    fun executeUpdateConfigurationFailed() {
        Mockito.`when`(repository.sendCurrentTime()).thenReturn(false)
        useCase.updateConfiguration()
        Mockito.verify(repository, Mockito.times(1)).sendCurrentTime()
        val classname = useCase::class.simpleName
        Mockito.verify(logger, Mockito.times(1)).warning("$classname: $ERROR_LOG")
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}