package sensor;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import models.Touch;

public class TouchSensor {

    private final static GpioController gpio = GpioFactory.getInstance();
    private final static GpioPinDigitalInput sensor = gpio.provisionDigitalInputPin(Touch.getPin(), PinPullResistance.PULL_DOWN);

    public static GpioPinDigitalInput getTouchSensor() {
        return sensor;
    }
}
