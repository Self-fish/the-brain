package collectHumidityAndBoxTempFeature.data.repository

import application.logger.LoggerWrapper
import collectHumidityAndBoxTempFeature.data.controller.DHT22Controller
import collectHumidityAndBoxTempFeature.data.controller.ParityCheckException
import collectHumidityAndBoxTempFeature.data.datamodel.DHT22ReadModel
import collectHumidityAndBoxTempFeature.domain.exceptions.BoxTemperatureSensorException
import collectHumidityAndBoxTempFeature.domain.exceptions.HumiditySensorException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import java.util.logging.Logger
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class HumidityAndTemperatureRepositoryImplTest {

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val dht22Controller = Mockito.mock(DHT22Controller::class.java)
    private val repository = HumidityAndTemperatureRepositoryImpl(dht22Controller, loggerWrapper, 1)


    @Test
    @DisplayName("When wiring not ready, we don't read data")
    fun notReadingWhenNoWiring() {
        Mockito.`when`(dht22Controller.checkGpioWiringSetup()).thenReturn(false)
        repository.collect()
        assertEquals(0.0, repository.temperature)
        assertEquals(0.0, repository.humidity)
    }


    @Test
    @DisplayName("When collecting the data, we read the sensor ten times")
    fun readingTenTimesTheSensorWhenCollectingTheData() {
        Mockito.`when`(dht22Controller.checkGpioWiringSetup()).thenReturn(true)
        repository.collect()
        Mockito.verify(dht22Controller, Mockito.times(10)).read()
    }

    @Test
    @DisplayName("The average temperature is calculated correctly")
    fun averageTemperatureIsCorrect() {
        Mockito.`when`(dht22Controller.read()).thenReturn(DHT22ReadModel(25.5, 50.0),
                DHT22ReadModel(30.5, 60.0),DHT22ReadModel(25.5, 50.0),
                DHT22ReadModel(30.5, 60.0), DHT22ReadModel(25.5, 50.0),
                DHT22ReadModel(30.5, 60.0), DHT22ReadModel(25.5, 50.0),
                DHT22ReadModel(30.5, 60.0), DHT22ReadModel(25.5, 50.0),
                DHT22ReadModel(30.5, 60.0))
        Mockito.`when`(dht22Controller.checkGpioWiringSetup()).thenReturn(true)
        repository.collect()
        assertEquals(28.0, repository.temperature)
    }

    @Test
    @DisplayName("The average humidity is calculated correctly")
    fun averageHumidityIsCorrect() {
        Mockito.`when`(dht22Controller.read()).thenReturn(DHT22ReadModel(25.5, 50.0),
                DHT22ReadModel(30.5, 60.0),DHT22ReadModel(30.5, 50.0),
                DHT22ReadModel(30.5, 60.0), DHT22ReadModel(30.5, 50.0),
                DHT22ReadModel(30.5, 60.0), DHT22ReadModel(30.5, 50.0),
                DHT22ReadModel(30.5, 60.0), DHT22ReadModel(30.5, 50.0),
                DHT22ReadModel(30.5, 60.0))
        Mockito.`when`(dht22Controller.checkGpioWiringSetup()).thenReturn(true)
        repository.collect()
        assertEquals(55.0, repository.humidity)
    }

    @Test
    @DisplayName("If all read are exceptions, then the temperature is 0.0")
    fun temperatureIsZeroWhenAllReadsAreExceptions() {
        Mockito.`when`(dht22Controller.read()).thenThrow(ParityCheckException())
        Mockito.`when`(dht22Controller.checkGpioWiringSetup()).thenReturn(true)
        repository.collect()
        assertEquals(0.0, repository.temperature)
    }

    @Test
    @DisplayName("If all read are exceptions, then the humidity is 0.0")
    fun humidityIsZeroWhenAllReadsAreExceptions() {
        Mockito.`when`(dht22Controller.read()).thenThrow(ParityCheckException())
        Mockito.`when`(dht22Controller.checkGpioWiringSetup()).thenReturn(true)
        repository.collect()
        assertEquals(0.0, repository.humidity)
    }

    @Test
    @DisplayName("If reading the temperature is 0.0, then a exception is launched")
    fun exceptionIsLaunchedWhenReadingTemperatureIsZero() {
        repository.temperature = 0.0
        val exception = assertThrows<BoxTemperatureSensorException> {
            repository.getCurrentTemperature()
        }
        assertTrue("The temperature is 0.0" == exception.message)
    }

    @Test
    @DisplayName("If reading the humidity is 0.0, then a exception is launched")
    fun exceptionIsLaunchedWhenReadingHumidityIsZero() {
        repository.humidity = 0.0
        val exception = assertThrows<HumiditySensorException> {
            repository.getCurrentHumidity()
        }
        assertTrue("The humidity is 0.0" == exception.message)
    }

    @Test
    @DisplayName("If reading the temperature is different to 0.0, then the value is returned")
    fun valueIsReturnedWhenReadingTemperatureIsDifferentZero() {
        repository.temperature = 25.5
        assertTrue(25.5 == repository.temperature)
    }

    @Test
    @DisplayName("If reading the humidity is different to 0.0, then the value is returned")
    fun valueIsReturnedWhenReadingHumidityIsDifferentZero() {
        repository.temperature = 50.0
        assertTrue(50.0 == repository.temperature)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}