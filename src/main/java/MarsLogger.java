import java.io.FileWriter;
import java.io.IOException;

public class MarsLogger implements AutoCloseable {

    private final FileWriter fileWriter;

    public MarsLogger(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }
    // Alle Threads deler den samme log som kan medfører skrive problemer, en af de nuværende problemer at de skriver inconsistent, så Temp starter først men skriver ikke altid som den første.
    public synchronized void write(String message) throws IOException {
        try{
            fileWriter.write("\n" + message);
        } catch (IOException e) {
            throw new IOException("File writer failed and crashed");
        }
    }

    @Override
    public void close() throws Exception {
        fileWriter.close();
    }
}