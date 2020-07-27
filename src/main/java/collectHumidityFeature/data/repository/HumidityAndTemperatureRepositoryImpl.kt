package collectHumidityFeature.data.repository

import application.logger.LoggerWrapper
import collectHumidityFeature.data.controller.DHT22Controller
import collectHumidityFeature.domain.contracts.HumidityAndTemperatureRepository
import com.pi4j.wiringpi.Gpio
import collectHumidityFeature.domain.exceptions.HumiditySensorException

class HumidityAndTemperatureRepositoryImpl(
        private val dht22Controller: DHT22Controller,
        private val logger: LoggerWrapper): HumidityAndTemperatureRepository {

    companion object {
        private const val MIN_TIME_BETWEEN_READS = 2500
        private const val MAX_READS = 10
    }

    var humidity: Double = 0.0
    var temperature: Double = 0.0

    override fun collect() {
        var errorCounter = 0

        if (Gpio.wiringPiSetup() == -1) {
            logger.severe(this::class.java.simpleName, "Failed initialising DHT22 Sensor")
        } else {
            for (i in 0 until MAX_READS) {
                try {
                    dht22Controller.read()
                    storeValues(dht22Controller.humidity, dht22Controller.temperature)
                } catch (e: Exception) {
                    errorCounter ++
                }
                Thread.sleep(MIN_TIME_BETWEEN_READS.toLong())
            }
            if(errorCounter == 10) {
                logger.severe(this::class.java.simpleName, "Error reading DHT22 sensor")
            }
        }
    }

    override fun getCurrentTemperature() = temperature

    override fun getCurrentHumidity(): Double {
        if(humidity == 0.0) {
            throw HumiditySensorException("The humidity is 0.0")
        } else {
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