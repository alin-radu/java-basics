package section20IOfiles.readingFiles;

import java.nio.charset.Charset;

public class MainNIO2 {

    public static void main(String[] args) {

        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset());


    }
}
