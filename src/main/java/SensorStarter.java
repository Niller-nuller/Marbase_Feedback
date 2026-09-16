import java.io.IOException;

public class SensorStarter {

    public static void main(String[] args) {

            Sensor temperatureSensor = new Sensor("TEMPERATURE");
            Sensor oxygenSenor = new Sensor("OXYGEN");
            Sensor pressureSensor = new Sensor("PRESSURE");
            Sensor co2Sensor = new Sensor("CO2");
            Thread temperatureSensorThread = new Thread(temperatureSensor);
            temperatureSensorThread.start();
            Thread oxygenSensorThread = new Thread(oxygenSenor);
            oxygenSensorThread.start();
            Thread pressureSensorThread = new Thread(pressureSensor);
            pressureSensorThread.start();
            Thread co2SensorThread = new Thread(co2Sensor);
            co2SensorThread.start();
    }
}

