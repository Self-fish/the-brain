package collectHumidityAndBoxTempFeature.data.repository

import application.logger.LoggerWrapper
import collectHumidityAndBoxTempFeature.data.controller.DHT22Controller
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import java.util.logging.Logger
import kotlin.test.assertTrue

internal class HumidityAndTemperatureRepositoryImplTest {

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val dht22Controller = Mockito.mock(DHT22Controller::class.java)
    private val repository = HumidityAndTemperatureRepositoryImpl(dht22Controller, loggerWrapper, 1)


    @Test
    @DisplayName("When collecting the data, we read the sensor ten times")
    fun readingTenTimesTheSensorWhenCollectingTheData() {
        Mockito.`when`(dht22Controller.checkGpioWiringSetup()).thenReturn(true)
        repository.collect()
        Mockito.verify(dht22Controller, Mockito.times(10)).read()
        assertTrue(true)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}