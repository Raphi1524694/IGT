import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    static String output = "hibernate.cfg.xml";
    static String base = "./src/main/resources/";
    public static void main(String[] args) throws IOException {
        String artsyArt = "\n" +
                "__/\\\\\\\\\\\\\\\\\\\\\\_        _____/\\\\\\\\\\\\\\\\\\\\\\\\_        __/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_        \n" +
                " _\\/////\\\\\\///__        ___/\\\\\\//////////__        _\\///////\\\\\\/////__       \n" +
                "  _____\\/\\\\\\_____        __/\\\\\\_____________        _______\\/\\\\\\_______      \n" +
                "   _____\\/\\\\\\_____        _\\/\\\\\\____/\\\\\\\\\\\\\\_        _______\\/\\\\\\_______     \n" +
                "    _____\\/\\\\\\_____        _\\/\\\\\\___\\/////\\\\\\_        _______\\/\\\\\\_______    \n" +
                "     _____\\/\\\\\\_____        _\\/\\\\\\_______\\/\\\\\\_        _______\\/\\\\\\_______   \n" +
                "      _____\\/\\\\\\_____        _\\/\\\\\\_______\\/\\\\\\_        _______\\/\\\\\\_______  \n" +
                "       __/\\\\\\\\\\\\\\\\\\\\\\_        _\\//\\\\\\\\\\\\\\\\\\\\\\\\/__        _______\\/\\\\\\_______ \n" +
                "        _\\///////////__        __\\////////////____        _______\\///________\n";
        System.out.println(artsyArt);
        System.out.println("Building " + args[0] + " version");
        Files.copy(new File(base + args[0]).toPath(), new File(base + output).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        Files.copy(new File(args[0]+"pom.xml").toPath(), new File("pom.xml").toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
}
