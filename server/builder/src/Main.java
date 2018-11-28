import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    static String output = "hibernate.cfg.xml";
    static String base = "./src/main/resources/";
    public static void main(String[] args) throws IOException {
        Files.copy(new File(base + args[0]).toPath(), new File(base + output).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
}
