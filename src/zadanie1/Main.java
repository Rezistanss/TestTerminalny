package zadanie1;
//dana jest tablica intow o rozmiarze 200 mln elementow, elementy maja losowe wartosci z pelnego zakresu inta.
//- policz ile czasu potrwa liczenie sumy wszystkich elementow uzywajac 1 watku
//- policz ile czasu potrwa liczenie sumy wszystkich elementow 2 watkow
//- policz ile czasu potrwa liczenie sumy wszystkich elementow 4 watkow
//- policz ile czasu potrwa liczenie sumy wszystkich elementow 8 watkow
//w komentarzu wypisz raport jak postępuje czas wraz ze wzrostem ilosci watkow. [lub jak spada]

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] array = generateRandomArray(200000000); // Generowanie tablicy z 200 mln elementami

        long startTime;
        long endTime;

        // Liczenie sumy przy użyciu 1 wątku
        startTime = System.currentTimeMillis();
        long sumSingleThread = calculateSum(array, 0, array.length);
        endTime = System.currentTimeMillis();
        System.out.println("Single thread sum: " + sumSingleThread);
        System.out.println("Time taken with 1 thread: " + (endTime - startTime) + " ms");
        //Przy użyciu 1 wątku zajmuje to około 70-80ms


        // Liczenie sumy przy użyciu 2 wątków
        startTime = System.currentTimeMillis();
        long sumTwoThreads = calculateSumMultiThread(array, 2);
        endTime = System.currentTimeMillis();
        System.out.println("Two threads sum: " + sumTwoThreads);
        System.out.println("Time taken with 2 threads: " + (endTime - startTime) + " ms");
        //Przy użyciu 2 wątków czas zmniejsza się niemal dwukrotnie, zajmuje to między 38-45ms


        // Liczenie sumy przy użyciu 4 wątków
        startTime = System.currentTimeMillis();
        long sumFourThreads = calculateSumMultiThread(array, 4);
        endTime = System.currentTimeMillis();
        System.out.println("Four threads sum: " + sumFourThreads);
        System.out.println("Time taken with 4 threads: " + (endTime - startTime) + " ms");
        //Przy użyciu 4 wątków czas zmniejsza się i wyliczenie zajmuje już około 30ms


        // Liczenie sumy przy użyciu 8 wątków
        startTime = System.currentTimeMillis();
        long sumEightThreads = calculateSumMultiThread(array, 8);
        endTime = System.currentTimeMillis();
        System.out.println("Eight threads sum: " + sumEightThreads);
        System.out.println("Time taken with 8 threads: " + (endTime - startTime) + " ms");
        //Przy użyciu 8 wątków czas zmniejsza się jeszcze bardziej i wyliczenie zajmuje około 26ms

        /*
        Wnioski:
        Użycie większej ilości wątków ma realny wpływ na działanie programu, lecz wpływa to na wykorzystanie sprzętowe.
        W moim przypadku spokojnie by wystarczyło użyć 4 wątków, ponieważ różnica między użyciem 4 a 8 wątków jest bardzo mała,
        zaledwie 4ms.
        Największą różnicę widać gdy porównamy wyliczenie przy pomocy jednego wątka i przy pomocy dwóch. Niemal o 50% skraca się czas
        ładowania/ wyliczania, a nie zwiększa to, aż tak bardzo wymagań sprzętowych
         */
    }

    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    public static long calculateSum(int[] array, int start, int end) {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        return sum;
    }

    public static long calculateSumMultiThread(int[] array, int numThreads) {
        int segmentSize = array.length / numThreads;
        SumThread[] threads = new SumThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int start = i * segmentSize;
            int end = (i == numThreads - 1) ? array.length : (i + 1) * segmentSize;
            threads[i] = new SumThread(array, start, end);
            threads[i].start();
        }
        long totalSum = 0;
        try {
            for (SumThread thread : threads) {
                thread.join();
                totalSum += thread.getPartialSum();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return totalSum;
    }
}
