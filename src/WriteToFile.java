import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

    public static void writeToFile(String line) throws IOException {
        String FileName = "logfile.txt";
        FileWriter myWriter = new FileWriter(FileName, true);
        BufferedWriter writer = new BufferedWriter(myWriter);

        writer.write(line);
        writer.close();
    }
}
