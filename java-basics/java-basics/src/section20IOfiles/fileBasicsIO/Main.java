package section20IOfiles.fileBasicsIO;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        // root files
        int idx = 0;
        for (File f : File.listRoots()) {
            System.out.println("file root no. " + idx + " " + f);
            idx++;
        }

        // cwd
        var currentWorkingDirectory = new File("").getAbsolutePath();
        System.out.println("- Current Working Directory (cwd): " + currentWorkingDirectory);

        String filename = "files/testing.csv";

        // java.io
        File file = new File(new File("").getAbsolutePath(), filename);
        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("1. I can't run unless this file exists");
            return;
        }
        System.out.println("1. I'm good to go.");

        // java.nio
        Path path = Paths.get("files/testing.csv");
        System.out.println(path.toAbsolutePath());
        if (!Files.exists(path)) {
            System.out.println("2. I can't run unless this file exists");
            return;
        }
        System.out.println("2. I'm good to go.");

    }
}

