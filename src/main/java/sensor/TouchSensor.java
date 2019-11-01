package sensor;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import models.Touch;

public class TouchSensor {

    private final static GpioController gpio = GpioFactory.getInstance();
    private final static GpioPinDigitalInput sensor = gpio.provisionDigitalInputPin(Touch.getPin(), PinPullResistance.PULL_DOWN);

    public static GpioPinDigitalInput getTouchSensor() {
        return sensor;
    }

    public static boolean getState() {
        return sensor.isHigh();
    }

}
