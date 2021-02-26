import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GetKeyValue extends Database {

    public static String get(String logfile, Integer offset) throws IOException {

        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(logfile), StandardCharsets.UTF_8));
        String line = fileContent.get(offset - 1);
        String value = line.split(",")[1];
        return value;
    }
}
