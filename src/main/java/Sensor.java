import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Sensor implements Runnable {

    private final int PORT = 5000;
    private final String HOST = "localhost";
    private final String sensorType;

    public Sensor(String sensorType) {
        this.sensorType = sensorType;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {

            System.out.println(sensorType + " Sensor is Starting Operation");
            while (true) {
                Thread.sleep(2500);
                //Står for at lave sensor data
                switch (sensorType) {
                    case "TEMPERATURE":
                        int min = 0;
                        int max = 50;
                        int range = max - min + 1;
                        int temp1 = (int) (Math.random() * range) + min;
                        int temp2 = (int) (Math.random() * range) + min;
                        double realTemp = temp1 - temp2 + Math.random();
                        String temperatureInfo = "TEMP:" + realTemp;
                        sendInfo(printWriter, temperatureInfo,socket);
                        break;
                    case "OXYGEN":
                        int min1 = 18;
                        int max1 = 25;
                        int range1 = max1 - min1 + 1;
                        int O2 = (int) (Math.random() * range1) + min1;
                        String O2Info = "O2:" + O2;
                        sendInfo(printWriter, O2Info,socket);
                        break;
                    case "PRESSURE":
                        int min2 = 700;
                        int max2 = 1200;
                        int range2 = max2 - min2 + 1;
                        int PSI = (int) (Math.random() * range2) + min2;
                        String  PSIInfo = "PSI:" + PSI;
                        sendInfo(printWriter, PSIInfo,socket);
                        break;
                    case "CO2":
                        int min3 = 1500;
                        int max3 = 2100;
                        int range3 = max3 - min3 + 1;
                        int CO2 = (int) (Math.random() * range3) + min3;
                        String CO2Info = "CO2:" + CO2;
                        sendInfo(printWriter, CO2Info,socket);
                        break;
                        default:
                            System.out.println(sensorType + " Sensor failure");
                            break;
                }
                //Den her thread.sleep og den over switch er for at simulerer tid mellem data indsamling
                Thread.sleep(2500);
                //Tjekker om der er kommet noget tilbage fra serveren og printer det ud
                if(bufferedReader.ready()) {
                    System.out.println(bufferedReader.readLine());
                }
            }
        } catch (SocketException| InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (SocketTimeoutException e) {
            System.out.println(e.getMessage());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void sendInfo(PrintWriter printWriter, String sensorInfo, Socket socket) throws IOException {
        //sender dataen til serveren
        printWriter.println(sensorInfo);
    }

}
