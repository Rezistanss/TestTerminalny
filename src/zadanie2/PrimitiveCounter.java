package zadanie2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrimitiveCounter {
    public static Map<String, Integer> countPrimitives(File... files) {
        Map<String, Integer> countMap = new ConcurrentHashMap<>();
        CountDownLatch latch = new CountDownLatch(files.length);

        for (File file : files) {
            new Thread(() -> {
                if (file.exists() && file.isFile() && isJavaFile(file)) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;

                        while ((line = br.readLine()) != null) {
                            if (!line.isBlank()) {
                                // check and skip if it's multiline comment
                                line = skipMultilineComment(br, line.trim());

                                // checks if line is commented and skip
                                while (line.trim().startsWith("//")) {
                                    line = br.readLine();
                                }

                                // check if line is not primitive type and skip
                                if (!line.isBlank() && Character.isUpperCase(line.charAt(0)))
                                    line = br.readLine();

                                countPrimitivesInLine(line, countMap);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return countMap;
    }

    private static String skipMultilineComment(BufferedReader br, String line) throws IOException {
        if (line.startsWith("/*")) {
            // skips all lines until multiline comment ends
            while (!line.contains("*/")) {
                line = br.readLine();
            }
        }
        return line.trim();
    }

    private static boolean isJavaFile(File file) {
        String fileName = file.getName();
        return fileName.endsWith(".java");
    }

    private static void countPrimitivesInLine(String line, Map<String, Integer> countMap) {
        List<String> primitiveTypes = List.of("int", "double", "float", "boolean", "char", "byte", "short", "long");
        primitiveTypes.forEach(type -> {
            Pattern pattern = Pattern.compile("\\b" + type + "\\b");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                if (!isInQuotes(line, matcher.start())) {
                    synchronized (countMap) {
                        countMap.merge(type, 1, Integer::sum);
                    }
                }
            }
        });
    }

    private static boolean isInQuotes(String line, int index) {
        int singleQuoteIndex = line.indexOf("'", index);
        int doubleQuoteIndex = line.indexOf("\"", index);

        return (singleQuoteIndex >= 0 && singleQuoteIndex < index) ||
                (doubleQuoteIndex >= 0 && doubleQuoteIndex < index);
    }
}