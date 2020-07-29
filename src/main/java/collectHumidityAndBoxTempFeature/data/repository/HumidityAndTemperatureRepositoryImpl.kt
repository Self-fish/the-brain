package collectHumidityAndBoxTempFeature.data.repository

import application.logger.LoggerWrapper
import collectHumidityAndBoxTempFeature.data.controller.DHT22Controller
import collectHumidityAndBoxTempFeature.domain.contracts.HumidityAndTemperatureRepository
import collectHumidityAndBoxTempFeature.domain.exceptions.BoxTemperatureSensorException
import com.pi4j.wiringpi.Gpio
import collectHumidityAndBoxTempFeature.domain.exceptions.HumiditySensorException
import java.math.RoundingMode

class HumidityAndTemperatureRepositoryImpl(
        private val dht22Controller: DHT22Controller,
        private val logger: LoggerWrapper,
        private val minTimeBetweenReads: Int): HumidityAndTemperatureRepository {

    companion object {
        private const val MAX_READS = 10
    }

    var humidity: Double = 0.0
    var temperature: Double = 0.0

    override fun collect(): Boolean {
        var errorCounter = 0
        var successCounter = 0
        var humidityRead = 0.0
        var temperatureRead = 0.0
        if (!dht22Controller.checkGpioWiringSetup()) {
            logger.severe(this::class.java.simpleName, "Failed initialising DHT22 Sensor")
            return false
        } else {
            for (i in 0 until MAX_READS) {
                try {
                    dht22Controller.read()
                    humidityRead += dht22Controller.humidity
                    temperatureRead += dht22Controller.temperature
                    successCounter++
                } catch (e: Exception) {
                    errorCounter ++
                }
                Thread.sleep(minTimeBetweenReads.toLong())
            }
            return if(errorCounter == 10) {
                logger.severe(this::class.java.simpleName, "Error reading DHT22 sensor")
                false
            } else {
                storeValues(roundValue(humidityRead/successCounter),
                        roundValue(temperatureRead/successCounter))
                true
            }
        }
    }

    private fun roundValue(value: Double): Double {
        return value.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }

    override fun getCurrentTemperature(): Double {
        if(temperature == 0.0) {
            logger.warning(this::class.java.simpleName, "The temperature read by the sensor is 0.0ºC")
            throw BoxTemperatureSensorException("The temperature is 0.0")
        } else {
            logger.fine(this::class.java.simpleName, "The temperature read by the sensor " +
                    "is ${temperature}ºC")
            return temperature
        }
    }

    override fun getCurrentHumidity(): Double {
        if(humidity == 0.0) {
            logger.warning(this::class.java.simpleName, "The humidity read by the sensor is 0.0%")
            throw HumiditySensorException("The humidity is 0.0")
        } else {
            logger.fine(this::class.java.simpleName, "The humidity read by the sensor " +
                    "is $humidity%")
            return humidity
        }
    }

    private fun storeValues(humidity: Double, temperature: Double) {
        this.temperature = temperature
        logger.fine(this::class.java.simpleName, "Box temperature: $temperature")
        this.humidity = humidity
        logger.fine(this::class.java.simpleName, "Box humidity: $humidity")
    }
}