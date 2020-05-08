import binomialHeap.BinomialHeap;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class BinomialHeapTest {
    private final int SIZE = 100;
    private BinomialHeap<Integer>[] binomialHeaps = new BinomialHeap[SIZE];
    private ActualValue[] actualValues = new ActualValue[SIZE];

    private static class ActualValue{
        int size = 0;
        int min = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        boolean isItEmpty = true;
    }

    private int randomHeapSize() {
        return 100 + (int)(Math.random()*1000);
    }

    private int randomValue() {
        return -500 + (int)(Math.random()*1500);
    }

    @BeforeEach
    void setUp() {
        for (int i = 0; i < SIZE; i++) {
            binomialHeaps[i] = new BinomialHeap<Integer>();
            actualValues[i] = new ActualValue();

            actualValues[i].size = randomHeapSize();

            for (int j = 0; j < actualValues[i].size; j++) {
                int value = randomValue();
                binomialHeaps[i].insert(value);

                actualValues[i].isItEmpty = false;

                if (value <= actualValues[i].min) {
                    actualValues[i].min2 = actualValues[i].min;
                    actualValues[i].min = value;
                }
                else {
                    if (value < actualValues[i].min2) {
                        actualValues[i].min2 = value;
                    }
                }

            }
        }
    }

    @Test
    void size() {
        int[] expected = new int[SIZE];
        int[] actual = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            expected[i] = binomialHeaps[i].size();
            actual[i] = actualValues[i].size;
        }
        Assert.assertArrayEquals(expected,actual);
    }

    @Test
    void isEmpty() {
        boolean[] expected = new boolean[SIZE];
        boolean[] actual = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            expected[i] = binomialHeaps[i].isEmpty();
            actual[i] = actualValues[i].isItEmpty;
        }

        Assert.assertArrayEquals(expected,actual);
    }

    @Test
    void clear() {
        boolean[] expected = new boolean[SIZE];
        boolean[] actual = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            binomialHeaps[i].clear();
            expected[i] = binomialHeaps[i].isEmpty();
            actual[i] = true;
        }

        Assert.assertArrayEquals(expected,actual);

    }

    @Test
    void min() {
        int[] expected = new int[SIZE];
        int[] actual = new int[SIZE];

        for (int i = 0; i < SIZE; i++) {
            expected[i] = binomialHeaps[i].min();
            actual[i] = actualValues[i].min;
        }
        Assert.assertArrayEquals(expected,actual);
    }

    @Test
    void delMin() {
        int[] expected = new int[SIZE];
        int[] actual = new int[SIZE];

        for (int i = 0; i < SIZE; i++) {
            binomialHeaps[i].delMin();
            expected[i] = binomialHeaps[i].min();
            actual[i] = actualValues[i].min2;
        }
        Assert.assertArrayEquals(expected,actual);
    }

}