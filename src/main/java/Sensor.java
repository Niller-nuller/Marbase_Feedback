import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class Sensor implements Runnable {

    private final int PORT = 5000;
    private final String HOST = "localhost";
    private final String sensorType;
    private volatile boolean running = true;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public Sensor(String sensorType) {
        this.sensorType = sensorType;
    }

    public void shutdown() {
        this.running = false;
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        System.out.println(sensorType + " Sensor is Starting Operation");

        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                try (Socket socket = new Socket(HOST, PORT)) {
                    socket.setSoTimeout(5000);
                    printWriter = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

                    String sensorInfo = generateReading();
                    if (sensorInfo != null && !sensorInfo.isEmpty()) {
                        sendInfo(printWriter, sensorInfo);
                    }

                    try {
                        String response = bufferedReader.readLine();
                        if (response != null && !response.isEmpty()) {
                            System.out.println(response);
                        }
                    } catch (SocketTimeoutException e) {
                        // Server did not respond within timeout. This is acceptable for the sensor.
                    }
                }

                Thread.sleep(2500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            } catch (IOException e) {
                System.out.println(sensorType + " connection failed, retrying... " + e.getMessage());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        }
    }

    private String generateReading() {
        switch (sensorType) {
            case "TEMPERATURE":
                int min = 0;
                int max = 50;
                int range = max - min + 1;
                int temp1 = (int) (Math.random() * range) + min;
                int temp2 = (int) (Math.random() * range) + min;
                double realTemp = temp1 - temp2 + Math.random();
                return "TEMP:" + realTemp;
            case "OXYGEN":
                int min1 = 18;
                int max1 = 25;
                int range1 = max1 - min1 + 1;
                int oxygen = (int) (Math.random() * range1) + min1;
                return "O2:" + oxygen;
            case "PRESSURE":
                int min2 = 700;
                int max2 = 1200;
                int range2 = max2 - min2 + 1;
                int psi = (int) (Math.random() * range2) + min2;
                return "PSI:" + psi;
            case "CO2":
                int min3 = 1500;
                int max3 = 2100;
                int range3 = max3 - min3 + 1;
                int carbonDioxide = (int) (Math.random() * range3) + min3;
                return "CO2:" + carbonDioxide;
            default:
                System.out.println(sensorType + " Sensor failure");
                return "";
        }
    }

    private void sendInfo(PrintWriter printWriter, String sensorInfo) {

        printWriter.println(sensorInfo);
    }
}
