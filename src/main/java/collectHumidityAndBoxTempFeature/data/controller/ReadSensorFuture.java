package collectHumidityAndBoxTempFeature.data.controller;


import com.pi4j.wiringpi.Gpio;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Callable;

class ReadSensorFuture implements Callable<byte[]>, Closeable {

/* Author: dougculnane (https://github.com/dougculnane/java-pi-thing)

                                Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/
*/

    private static final int LONGEST_ZERO = 50000;

    private boolean keepRunning = true;
    private int pinNumber = 0;


    public ReadSensorFuture(int pinNumber) {
        this.pinNumber = pinNumber;
        Gpio.pinMode(pinNumber, Gpio.OUTPUT);
        Gpio.digitalWrite(pinNumber, Gpio.HIGH);
    }

    @Override
    public byte[] call() throws Exception {

        // do expensive (slow) stuff before we start and privoritize thread.
        byte[] data = new byte[5];
        long startTime = System.nanoTime();
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        sendStartSignal();
        waitForResponseSignal();
        for (int i = 0; i < 40; i++) {
            while (keepRunning && Gpio.digitalRead(pinNumber) == Gpio.LOW) {
            }
            startTime = System.nanoTime();
            while (keepRunning && Gpio.digitalRead(pinNumber) == Gpio.HIGH) {
            }
            long timeHight = System.nanoTime() - startTime;
            data[i / 8] <<= 1;
            if ( timeHight > LONGEST_ZERO) {
                data[i / 8] |= 1;
            }
        }

        Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
        return data;
    }

    private void sendStartSignal() {
        // Send start signal.
        Gpio.pinMode(pinNumber, Gpio.OUTPUT);
        Gpio.digitalWrite(pinNumber, Gpio.LOW);
        Gpio.delay(10);
        Gpio.digitalWrite(pinNumber, Gpio.HIGH);
    }

    /**
     * AM2302 will pull low 80us as response signal, then
     * AM2302 pulls up 80us for preparation to send data.
     */
    private void waitForResponseSignal() {
        Gpio.pinMode(pinNumber, Gpio.INPUT);
        while (keepRunning && Gpio.digitalRead(pinNumber) == Gpio.HIGH) {
        }
        while (keepRunning && Gpio.digitalRead(pinNumber) == Gpio.LOW) {
        }
        while (keepRunning && Gpio.digitalRead(pinNumber) == Gpio.HIGH) {
        }
    }

    @Override
    public void close() throws IOException {
        keepRunning = false;
        Gpio.pinMode(pinNumber, Gpio.OUTPUT);
        Gpio.digitalWrite(pinNumber, Gpio.HIGH);
    }
}