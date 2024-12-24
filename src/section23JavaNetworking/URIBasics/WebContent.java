package section23JavaNetworking.URIBasics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebContent {

    public static void main(String[] args) {

        try {
//            URL url = new URL("http://example.com");

            URL url = new URL("https://jsonplaceholder.typicode.com/todos?id=5");

//             executes openConnection().getInputStream;
//            printContents(url.openStream());

            URLConnection urlConnection = url.openConnection();

            System.out.println("----------------------------------------------------");
            System.out.println("getContentType() \n" + urlConnection.getContentType());
            System.out.println("----------------------------------------------------");

            // print header fields
            urlConnection.getHeaderFields()
                    .entrySet()
                    .forEach(System.out::println);

            // print a custom selected header
            System.out.println("----------------------------------------------------");
            System.out.println("getHeaderField() \n" + urlConnection.getHeaderField("Cache-Control"));
            System.out.println("----------------------------------------------------");

            urlConnection.connect();

            printContents(urlConnection.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printContents(InputStream is) {
        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = inputStream.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
