package collectHumidityAndBoxTempFeature.domain

import application.logger.LoggerWrapper
import collectHumidityAndBoxTempFeature.domain.contracts.HumidityAndTemperatureRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.logging.Logger

internal class CollectHumidityAndBoxTempUseCaseTest {

    private val repository = Mockito.mock(HumidityAndTemperatureRepository::class.java)
    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val useCase = CollectHumidityAndBoxTempUseCase(repository, loggerWrapper)

    @Test
    @DisplayName("When asking for collcetion the repository is called")
    fun collectMethodIsCalled() {
        useCase.collectTemperatureAndHumidity()
        Mockito.verify(repository, Mockito.times(1)).collect()
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }

}