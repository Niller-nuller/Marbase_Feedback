import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MarsServer {

    private final static int PORT = 5000;
    private final static int MAX_SENSORS = 5;
    private static ExecutorService executor;


    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
            try (ServerSocket serverSocket = new ServerSocket(PORT);
                 FileWriter fileWriter = new FileWriter("src/mars.log", true);
                 MarsLogger marsLogger = new MarsLogger(fileWriter)) {
                while (true) {
                    executor = Executors.newFixedThreadPool(MAX_SENSORS);
                    Socket socket = serverSocket.accept();
                    System.out.println("New Sensor connected");
                    executor.submit(new MarsProtocol(socket, marsLogger));

                }
            } catch (IOException e) {

            } catch (Exception e){
                e.printStackTrace();
            }
            finally {
                executor.shutdown();
            }
        }

}
