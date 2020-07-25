package showBoxHumidityFeature.data.repository

import com.pi4j.io.gpio.RaspiPin
import com.pi4j.wiringpi.Gpio
import showBoxHumidityFeature.data.controller.DHT22
import showBoxHumidityFeature.domain.contract.repository.BoxHumidityRepository
import java.io.IOException
import java.util.concurrent.TimeoutException


class BoxHumidityRepositoryImpl: BoxHumidityRepository {

    override fun getBoxHumidity(): Float {
        println("Starting DHT22")
        if (Gpio.wiringPiSetup() == -1) {
            println("GPIO wiringPiSetup Failed!")
        } else {
            val dht22 = DHT22(RaspiPin.GPIO_02)
            val LOOP_SIZE = 10
            var countSuccess = 0
            for (i in 0 until LOOP_SIZE) {
                println()
                try {
                    dht22.read()
                    System.out.println("Humidity=" + dht22.humidity.toString() +
                            "%, Temperature=" + dht22.temperature.toString() + "*C")
                    countSuccess++
                } catch (e: TimeoutException) {
                    System.err.println("ERROR: $e")
                } catch (e: Exception) {
                    System.err.println("ERROR: $e")
                }
                Thread.sleep(DHT22.MIN_MILLISECS_BETWEEN_READS.toLong())
            }
            println("Read success rate: $countSuccess / $LOOP_SIZE")

            try {
                println()
                println("Running read loop method")
                dht22.doReadLoop()
                System.out.println("Humidity=" + dht22.humidity.toString() +
                        "%, Temperature=" + dht22.temperature.toString() + "*C")
            } catch (e: IOException) {
                System.err.println("ERROR: $e")
            }
        }
        return 0F
    }

}