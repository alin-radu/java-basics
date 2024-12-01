package section20IOfiles.readingFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainNIO2 {

    public static void main(String[] args) {

//        System.out.println(System.getProperty("file.encoding"));
//        System.out.println(Charset.defaultCharset());

        Path path = Path.of("files/fixedWidth.txt");

        try {
            var filesReadAllBytes = Files.readAllBytes(path);
            var stringFilesReadAllBytes = new String(filesReadAllBytes);
            var filesReadString = Files.readString(path);

            System.out.println(stringFilesReadAllBytes);
            System.out.println("-----------------------");
//            System.out.println(filesReadString);

            Pattern p = Pattern.compile("(.{15})(.{3})(.{12})(.{8})(.{2}).*");
            Set<String> values = new TreeSet<>();
            Files.readAllLines(path).forEach(s -> {
                if (!s.startsWith("Name")) {
                    Matcher m = p.matcher(s);
                    if (m.matches()) {
                        values.add(m.group(3).trim());
                    }
                }
            });
            System.out.println("1 " + values);

            try (var stringStream = Files.lines(path)) {
                var results = stringStream
                        .skip(1)
                        .map(p::matcher)
                        .filter(Matcher::matches)
                        .map(m -> m.group(3).trim())
                        .distinct()
                        .sorted()
                        .toArray(String[]::new);
                System.out.println("2 " + Arrays.toString(results));
            }

            try (var stringStream = Files.lines(path)) {
                var results = stringStream
                        .skip(1)
                        .map(p::matcher)
                        .filter(Matcher::matches)
                        .collect(Collectors.groupingBy(m -> m.group(3).trim(),
                                Collectors.counting()));

                results.entrySet().forEach(System.out::println);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
