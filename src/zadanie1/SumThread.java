package zadanie1;

public class SumThread extends Thread{
    private final int[] array;
    private final int start;
    private final int end;
    private long partialSum;

    public SumThread(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        partialSum = 0;
        for (int i = start; i < end; i++) {
            partialSum += array[i];
        }
    }

    public long getPartialSum() {
        return partialSum;
    }
}
