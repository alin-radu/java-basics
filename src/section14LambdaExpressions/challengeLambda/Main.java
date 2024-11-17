package section14LambdaExpressions.challengeLambda;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Main {

    public static void main(String[] args) {

        Consumer<String> printWords = new Consumer<String>() {

            @Override
            public void accept(String sentence) {
                String[] parts = sentence.split(" ");
                for (String part : parts) {
                    System.out.println(part);
                }
            }
        };

        Consumer<String> printWordsLambda = (sentence) -> {
            String[] parts = sentence.split(" ");
            for (String part : parts) {
                System.out.println(part);
            }
        };

        Consumer<String> printWordsForEach = (sentence) -> {
            String[] parts = sentence.split(" ");
            Arrays.asList(parts).forEach(s -> System.out.println(s));
        };

        Consumer<String> printWordsConcise = (sentence) ->
                Arrays.asList(sentence.split(" ")).forEach(s -> System.out.println(s));

//        System.out.println("---1---");
//        printWords.accept("Let's split this up into an array");
//        System.out.println("---2---");
//        printWordsLambda.accept("Let's split this up into an array");
//        System.out.println("---3---");
//        printWordsForEach.accept("Let's split this up into an array");
//        System.out.println("---4---");
//        printWordsConcise.accept("Let's split this up into an array");
//        System.out.println("---end---");

        // mini challenge 2
        UnaryOperator<String> everySecondChar = source -> {
            StringBuilder returnVal = new StringBuilder();
            for (int i = 0; i < source.length(); i++) {
                if (i % 2 == 1) {
                    returnVal.append(source.charAt(i));
                }
            }
            return returnVal.toString();
        };

        // mini challenge 3
        System.out.println(everySecondChar.apply("1234567890"));

        // mini challenge 4
        String result = everySecondCharacter(everySecondChar, "1234567890");
        System.out.println(result);

        // mini challenge 5
        Supplier<String> iLoveJava = () -> "I love Java!";
        Supplier<String> iLoveJava2 = () -> {
            return "I love Java!";
        };

        System.out.println(iLoveJava.get());
        System.out.println(iLoveJava2.get());
    }

    public static String everySecondChar(String source) {

        StringBuilder returnVal = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            if (i % 2 == 1) {
                returnVal.append(source.charAt(i));
            }
        }
        return returnVal.toString();
    }

    public static String everySecondCharacter(UnaryOperator<String> fn, String str) {
        return fn.apply(str);
    }

}