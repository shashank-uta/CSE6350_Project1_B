import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DelKey {

    public static void del(String logfile, Integer offset) throws IOException {

        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(logfile), StandardCharsets.UTF_8));
        fileContent.set(offset - 1, " ");
        Files.write(Paths.get(logfile), fileContent, StandardCharsets.UTF_8);
    }
}
