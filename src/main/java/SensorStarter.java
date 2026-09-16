import java.io.IOException;

public class SensorStarter {
//starter sensorerne
    public static void main(String[] args) {

            //Skaber diverse sensor der skal bruges
            Sensor temperatureSensor = new Sensor("TEMPERATURE");
            Sensor oxygenSenor = new Sensor("OXYGEN");
            Sensor pressureSensor = new Sensor("PRESSURE");
            Sensor co2Sensor = new Sensor("CO2");
            //laver en thread til hver af sensorerne og starter dem
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

