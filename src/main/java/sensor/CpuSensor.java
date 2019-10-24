package sensor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CpuSensor {

    // read temperature from system
    public static double getCPUtemperature()
    {
        String fileName = "/sys/class/thermal/thermal_zone0/temp";
        String line = null;

        float tempC = -1F;

        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null)
            {
                tempC = (Integer.parseInt(line) / 1000F);
            }
            bufferedReader.close();

        } catch (FileNotFoundException ex)
        {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex)
        {
            System.out.println("Error reading file '" + fileName + "'");
        }

        return tempC;
    }


}
