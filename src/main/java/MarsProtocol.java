import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MarsProtocol implements Runnable {

    private final Socket socket;
    private final MarsLogger marsLogger;
    private PrintWriter writer;

    public MarsProtocol(Socket socket, MarsLogger marsLogger) {
        this.socket = socket;
        this.marsLogger = marsLogger;
    }

    @Override
    public void run() { //Bliver startet med Submit() fra MarsServer
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); ){
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            receiveLoop(reader);

            } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
//Er vores main loop for protocol
    private void receiveLoop(BufferedReader reader) throws IOException {
        boolean running = true;
        try{
            while (running) { //En svaghed er at loopet aldrig ordenligt lukker, i stedet slutter det når en execoption bliver fanget.
            String line = reader.readLine();
            System.out.println(line);
            String[] parts = line.split(":", 2); //Vi splitter vores besked for at finde ud af hvad er det vi skal håndtaggere.
            String key = parts[0];
            String value = parts[1];
            switch(key) {
                case "TEMP"-> handleTemperatureValue(key, value);
                case "O2" -> handleOxygenValue(key, value);
                case "PSI" -> handlePressureValue(key, value);
                case "CO2" -> handleCo2Value(key, value);
                default -> sendMessage("No readable key value");
            }
            }
        } catch (IOException e){
            throw new IOException("Failed to receive message");
        }
    } //4 metoder til at håndtaggere hver deres egen value.
    public void handleTemperatureValue(String key, String value) throws IOException {
        double numberValue = Double.parseDouble(value);
        if(numberValue < -15 || numberValue > 35){
            sendMessage(numberValue + " celsius:TEMPERATURE IN DANGEROUS RANGE");
        }
        String dokument = getCurrentTime() + " " + key + ": " + value;
        System.out.println(dokument);
        marsLogger.write(dokument); //Her sender vi beskeden "dokument" til at blive skrevet ned i vores log mars.log
    }
    public void handleOxygenValue(String key, String value) throws IOException {
        int numberValue = Integer.parseInt(value);
        if(numberValue < 19 || numberValue > 23){
            sendMessage(numberValue + " procent:OXYGEN IN DANGEROUS RANGE");
        }
        String dokument = getCurrentTime() + " " + key + ": " + value;
        System.out.println(dokument);
        marsLogger.write(dokument);
    }
    public void handlePressureValue(String key, String value) throws IOException {
        int numberValue = Integer.parseInt(value);
        if(numberValue < 800 || numberValue > 1100){
            sendMessage(numberValue + " hPa:PRESSURE IN DANGEROUS RANGE");
        }
        String dokument = getCurrentTime() + " " + key + ": " + value;
        System.out.println(dokument);
        marsLogger.write(dokument);
    }
    public void handleCo2Value(String key, String value) throws IOException {
        int numberValue = Integer.parseInt(value);
        if(numberValue > 2000){
            sendMessage(numberValue + " ppm:CO2 IN DANGEROUS RANGE");
        }
        String dokument = getCurrentTime() + " " + key + ": " + value;
        System.out.println(dokument);
        marsLogger.write(dokument);
    }
    //Metode til at skrive beskeder til vores Sensor.
    private void sendMessage(String message) {
        writer.println(message);
    }
    //Til at få vores TimeStamp uden millisekunder.
    private String getCurrentTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(format);
    }
}
