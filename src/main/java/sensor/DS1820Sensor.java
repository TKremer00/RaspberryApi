package sensor;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;
import java.util.List;

public class DS1820Sensor {

    // method to get temperature
    public static double getTemperature()
    {
        // default -1 as error status;
        double value = - 1.0;

        W1Master master = new W1Master();
        List<W1Device> w1Devices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);

        for (W1Device device : w1Devices)
        {
            // this line is enought if you want to read the temperature
            // returns the temperature as double rounded to one decimal place after the point
            value = ((TemperatureSensor) device).getTemperature();
        }

        return value;
    }
}
