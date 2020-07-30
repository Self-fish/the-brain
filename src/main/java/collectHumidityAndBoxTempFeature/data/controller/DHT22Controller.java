package collectHumidityAndBoxTempFeature.data.controller;

import application.logger.LoggerWrapper;
import collectHumidityAndBoxTempFeature.data.datamodel.DHT22ReadModel;
import com.pi4j.wiringpi.Gpio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.*;

/* Author: dougculnane (https://github.com/dougculnane/java-pi-thing)

                                Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/
*/

public class DHT22Controller {

    private int pinNumber;
    private LoggerWrapper logger;
    private byte[] data = null;
    //private Double humidity = null;
    //private Double temperature = null;
    private Long lastRead = null;

    public DHT22Controller(int pinNumber, LoggerWrapper logger) {
        this.pinNumber = pinNumber;
        this.logger = logger;
    }

    public boolean checkGpioWiringSetup() {
        if (Gpio.wiringPiSetup() == -1) {
            return false;
        } else {
            return true;
        }
    }

    private void getData() throws IOException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ReadSensorFuture readSensor = new ReadSensorFuture(pinNumber);
        Future<byte[]> future = executor.submit(readSensor);
        data = new byte[5];
        try {
            data = future.get(2, TimeUnit.SECONDS);
            readSensor.close();
        } catch (Exception e) {
            logger.severe(this.getClass().getSimpleName(), "Exception " + e.toString());
            readSensor.close();
            future.cancel(true);
            executor.shutdown();
            throw new IOException(e);
        }
        readSensor.close();
        executor.shutdown();
    }

    public DHT22ReadModel read() throws Exception {
        return read(true);
    }

    private DHT22ReadModel read(boolean checkParity) throws IOException {

        double humidity;
        double temperature;

        checkLastReadDelay();
        lastRead = System.currentTimeMillis();
        getData();
        if (checkParity) {
            checkParity();
        }

        double newHumidityValue = getReadingValueFromBytes(data[0], data[1]);
        if (newHumidityValue >= 0 && newHumidityValue <= 100) {
            humidity = newHumidityValue;
        } else {
            logger.severe(this.getClass().getSimpleName(), "ValueOutOfOperatingRangeException");
            throw new ValueOutOfOperatingRangeException();
        }
        double newTemperatureValue = getReadingValueFromBytes(data[2], data[3]);
        if (newTemperatureValue >= -40 && newTemperatureValue < 85) {
            temperature = newTemperatureValue;
        } else {
            logger.severe(this.getClass().getSimpleName(), "ValueOutOfOperatingRangeException");
            throw new ValueOutOfOperatingRangeException();
        }
        lastRead = System.currentTimeMillis();
        return new DHT22ReadModel(temperature, humidity);
    }

    private void checkLastReadDelay() throws IOException {
        if (lastRead != null) {
            if (lastRead > System.currentTimeMillis() - 2000) {
                logger.warning(this.getClass().getSimpleName(), "Last read was under 2 seconds");
                throw new IOException("Last read was under 2 seconds ago. Please wait longer between reads!");
            }
        }
    }

    static double getReadingValueFromBytes(final byte hi, final byte low) {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put(hi);
        bb.put(low);
        short shortVal = bb.getShort(0);
        Double doubleValue = new Double(shortVal) / 10;

        if (1 == ((hi >> 7) & 1)) {
            doubleValue = (doubleValue + 3276.8) * -1d;
        }

        return doubleValue;
    }

    private void checkParity() throws ParityCheckException {
        if (!(data[4] == (data[0] + data[1] + data[2] + data[3]))) {
            logger.severe(this.getClass().getSimpleName(), "ParityCheckException");
            throw new ParityCheckException();
        }
    }

}