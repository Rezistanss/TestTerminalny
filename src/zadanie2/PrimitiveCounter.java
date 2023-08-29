package zadanie2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
                        boolean multiLineComment = false;

                        while ((line = br.readLine()) != null) {
                            if (multiLineComment) {
                                if (line.contains("*/")) {
                                    multiLineComment = false;
                                    line = line.substring(line.indexOf("*/") + 2);
                                } else {
                                    continue;
                                }
                            } else if (line.contains("/*")) {
                                multiLineComment = true;
                                int startCommentIndex = line.indexOf("/*");
                                int endCommentIndex = line.indexOf("*/", startCommentIndex);
                                if (endCommentIndex >= 0) {
                                    line = line.substring(0, startCommentIndex) + line.substring(endCommentIndex + 2);
                                } else {
                                    line = line.substring(0, startCommentIndex);
                                }
                            }

                            if (line.contains("//")) {
                                line = line.substring(0, line.indexOf("//"));
                            }

                            if (!line.contains("\"") && !line.contains("'") && !line.contains("/*") && !line.contains("*/")) {
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

    private static boolean isJavaFile(File file) {
        String fileName = file.getName();
        return fileName.endsWith(".java");
    }

    private static void countPrimitivesInLine(String line, Map<String, Integer> countMap) {
        String[] primitiveTypes = {"int", "double", "float", "boolean", "char", "byte", "short", "long"};
        for (String primitiveType : primitiveTypes) {
            Pattern pattern = Pattern.compile("\\b" + primitiveType + "\\b");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                synchronized (countMap) {
                    countMap.merge(primitiveType, 1, Integer::sum);
                }
            }
        }
    }
}
