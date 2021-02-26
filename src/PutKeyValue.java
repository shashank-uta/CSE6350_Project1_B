import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class PutKeyValue {

    public static Integer put(String logfile, String key, String value) throws IOException {
        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(logfile),StandardCharsets.UTF_8));

        fileContent.add(key + "," + value);

        Files.write(Paths.get(logfile), fileContent, StandardCharsets.UTF_8);

        return fileContent.size();
    }

}
