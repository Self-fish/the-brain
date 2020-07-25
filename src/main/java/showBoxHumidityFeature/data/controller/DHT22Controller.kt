package showBoxHumidityFeature.data.controller

import com.pi4j.wiringpi.Gpio
import com.pi4j.wiringpi.GpioUtil

class DHT22Controller: Runnable {

    val maxTimings = 85
    val dht22Dat = arrayOf(0, 0, 0, 0, 0)
    var temperature = 9999F
    var humidity = 9999F
    var shuttingDown = false

    init {
        if(Gpio.wiringPiSetup() == -1) {
            //Log the problems
            print("Holaaa")
        } else {
            GpioUtil.export(3, GpioUtil.DIRECTION_OUT)
        }
    }

    private fun pollDHT22(): Int {
        var lastState = Gpio.HIGH
        var j = 0
        val pinNumber = 2
        Gpio.pinMode(pinNumber, Gpio.OUTPUT)
        Gpio.digitalWrite(pinNumber, Gpio.LOW)
        Gpio.delay(18)
        Gpio.digitalWrite(pinNumber, Gpio.HIGH)
        Gpio.pinMode(pinNumber, Gpio.INPUT)

        for (i in 0 until maxTimings) {
            var counter = 0
            while (Gpio.digitalRead(pinNumber) == lastState) {
                counter++
                Gpio.delayMicroseconds(1)
                if (counter == 255) {
                    break
                }
            }
            lastState = Gpio.digitalRead(pinNumber)
            if (counter == 255) {
                break
            }

            if (i >= 4 && i % 2 == 0) {
                /* shove each bit into the storage bytes */
                dht22Dat[j / 8] = dht22Dat[j / 8] shl 1
                if (counter > 16) {
                    dht22Dat[j / 8] = dht22Dat[j / 8] or 1
                }
                j++
            }
        }
        return j
    }

    private fun refreshData() {
        val pollDataCheck = pollDHT22()
        if (pollDataCheck >= 40 && checkParity()) {
            val newHumidity = ((dht22Dat[0] shl 8) + dht22Dat[1]) as Float / 10
            val newTemperature = ((dht22Dat[2] and 0x7F shl 8) + dht22Dat[3]) as Float / 10
            if (humidity == 9999f || newHumidity < humidity + 40 && newHumidity > humidity - 40) {
                humidity = newHumidity
                if (humidity > 100) {
                    humidity = dht22Dat[0].toFloat() // for DHT22
                }
            }
            if (temperature == 9999f || newTemperature < temperature + 40 && newTemperature > temperature - 40) {
                temperature = ((dht22Dat[2] and 0x7F shl 8) + dht22Dat[3]) as Float / 10
                if (temperature > 125) {
                    temperature = dht22Dat[2].toFloat() // for DHT22
                }
                if (dht22Dat[2] and 0x80 != 0) {
                    temperature = -temperature
                }
            }
        }
    }

    private fun checkParity(): Boolean {
        return dht22Dat[4] == dht22Dat[0] + dht22Dat[1] + dht22Dat[2] + dht22Dat[3] and 0xFF
    }

    fun getHumidityValue(): Float {
        return (if (humidity == 9999f) {
            0
        } else humidity) as Float
    }

    fun getTemperatureValue(): Float {
        if (temperature == 9999F) {
            return 0F
        }
        return temperature;
    }

    override fun run() {
        while (!shuttingDown) {
            refreshData()
            println("Temperatura: ${getTemperatureValue()}")
        }
    }
}