package zadanie2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PrimitiveCounter {
    public static Map<String, Integer> countPrimitives(File... javaFiles) {
        Map<String, Integer> result = new HashMap<>();
        Thread[] threads = new Thread[javaFiles.length];
        for (int i = 0; i < javaFiles.length; i++) {
            final File file = javaFiles[i];
            threads[i] = new Thread(() -> {
                Map<String, Integer> fileCounter = countPrimitivesInFile(file);
                synchronized (result) {
                    mergeCounts(result, fileCounter);
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static void mergeCounts(Map<String, Integer> totalCounts, Map<String, Integer> countsToAdd) {
        for (Map.Entry<String, Integer> entry : countsToAdd.entrySet()) {
            totalCounts.put(entry.getKey(), totalCounts.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
    }

    private static Map<String, Integer> countPrimitivesInFile(File file) {
        Map<String, Integer> counts = new HashMap<>();
        if (!file.exists() || !file.isFile() || !file.getName().endsWith(".java")) {
            return counts;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                countPrimitiveDeclarations(line, counts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counts;
    }

    private static void countPrimitiveDeclarations(String line, Map<String, Integer> counts) {
        String[] primitiveTypes = {"int", "long", "short", "byte", "float", "double", "char", "boolean"};
        for (String primitiveType : primitiveTypes) {
            if (line.contains(primitiveType)) {
                counts.put(primitiveType, counts.getOrDefault(primitiveType, 0) + 1);
            }
        }
    }
}
