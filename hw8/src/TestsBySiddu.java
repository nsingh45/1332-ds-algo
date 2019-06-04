import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Tests sorting things. Ensures that your sorts handle
 * edge cases like empty arrays and already sorted arrays,
 * are efficient by checking the number of comparisons,
 * and actually work.
 * <p>
 * Note that this suite uses randomness. Run
 * this suite many, many times to ensure that your code
 * is resilient across many different sets of data. Edge cases
 * like already sorted arrays and worst case arrays are tested
 * independently.
 */
public class TestsBySiddu {

    private Sorting sorting;
    private Random random;

    public TestsBySiddu() {
        // don't need a new one each time
        sorting = new Sorting();
        random = new Random();
    }

    @AfterClass
    public static void printWarning() {
        System.out.println("Note: all of these sorts operated on different data sets. " +
                "It is not valid to\ncompare these sorts by their comparison counts using " +
                "this report.");
    }

    @Before
    public void setup() {
        MyCoolObject.comparisons = 0;
    }

    /**
     * Ensures that your code properly handles zero length arrays.
     * <p>
     * No array asserts here; there's no possible way that
     * your code could somehow modify these arrays, so
     * this is simply checking to ensure that no exceptions
     * are thrown during operation (and that no comparisons are made).
     */
    @Test(timeout = 250)
    public void testZeroLengthSorts() {
        sorting.bubblesort(new MyCoolObject[]{});
        sorting.insertionsort(new MyCoolObject[]{});
        sorting.selectionsort(new MyCoolObject[]{});
        sorting.quicksort(new MyCoolObject[]{}, null);
        sorting.mergesort(new MyCoolObject[]{});
        sorting.radixsort(new int[]{});
        assertEquals(0, MyCoolObject.comparisons);
    }

    /**
     * Ensures that your code properly handles one length arrays.
     */
    @Test(timeout = 250)
    public void testOneLengthSorts() {
        MyCoolObject[] array = new MyCoolObject[]{new MyCoolObject(1)};
        MyCoolObject[] clone = new MyCoolObject[]{new MyCoolObject(1)};
        sorting.bubblesort(clone);
        assertArrayEquals(array, clone);
        sorting.insertionsort(clone);
        assertArrayEquals(array, clone);
        sorting.selectionsort(clone);
        assertArrayEquals(array, clone);
        sorting.quicksort(clone, new Random());
        assertArrayEquals(array, clone);
        sorting.mergesort(clone);
        assertArrayEquals(array, clone);
        assertArrayEquals(new int[]{6},
                sorting.radixsort(new int[]{6}));
        assertEquals(0, MyCoolObject.comparisons);
    }

    /**
     * Tests bubble sort.
     * <p>
     * Bubble sort's maximum comparisons is defined by
     * (n - 1) + (n - 2) + ... + (1) = n(n - 1)/2.
     */
    @Test(timeout = 250)
    public void testBubbleSortRandomArray() {
        // 1 to 100 length array
        MyCoolObject[] array = new MyCoolObject[random.nextInt(100) + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.bubblesort(array);
        int maxComparisons = (array.length * (array.length - 1)) / 2;
        System.out.printf("Bubble Sort: Max comparisons: %d%n", maxComparisons);
        System.out.printf("Bubble Sort: Comparisons: %d%n", MyCoolObject.comparisons);
        assertFalse("Your bubble sort is inefficient.", MyCoolObject.comparisons > maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests insertion sort.
     * <p>
     * Insertion sort's maximum comparisons is defined by
     * (n - 1) + (n - 2) + ... + (1) = n(n - 1)/2.
     */
    @Test(timeout = 250)
    public void testInsertionSortRandomArray() {
        // 1 to 100 length array
        MyCoolObject[] array = new MyCoolObject[random.nextInt(100) + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.insertionsort(array);
        int maxComparisons = (array.length * (array.length - 1)) / 2;
        System.out.printf("Insertion Sort: Max comparisons: %d%n", maxComparisons);
        System.out.printf("Insertion Sort: Comparisons: %d%n", MyCoolObject.comparisons);
        assertFalse("Your insertion sort is inefficient.", MyCoolObject.comparisons > maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests selection sort.
     * <p>
     * Selection sort's maximum comparisons is defined by
     * (n - 1) + (n - 2) + ... + (1) = n(n - 1)/2.
     */
    @Test(timeout = 250)
    public void testSelectionSortRandomArray() {
        // 1 to 100 length array
        MyCoolObject[] array = new MyCoolObject[random.nextInt(100) + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.selectionsort(array);
        int maxComparisons = (array.length * (array.length - 1)) / 2;
        System.out.printf("Selection Sort: Max comparisons: %d%n", maxComparisons);
        System.out.printf("Selection Sort: Comparisons: %d%n", MyCoolObject.comparisons);
        assertFalse("Your selection sort is inefficient.", MyCoolObject.comparisons > maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests quick sort.
     * <p>
     * Quick sort's maximum comparisons is defined by
     * n + (n - 1) + (n - 2) + ... + (1) = n^2/2.
     */
    @Test(timeout = 250)
    public void testQuicksortSortRandomArray() {
        // 1 to 100 length array
        MyCoolObject[] array = new MyCoolObject[random.nextInt(100) + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.quicksort(array, random);
        int maxComparisons = (array.length * array.length) / 2;
        System.out.printf("Quick Sort: Max comparisons: %d%n", maxComparisons);
        System.out.printf("Quick Sort: Comparisons: %d%n", MyCoolObject.comparisons);
        assertFalse("Your quick sort is inefficient.", MyCoolObject.comparisons > maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests merge sort.
     * <p>
     * Merge sort's maximum comparisons is defined by
     * n*log2(n).
     */
    @Test(timeout = 250)
    public void testMergesortSortRandomArray() {
        // 1 to 100 length array
        MyCoolObject[] array = new MyCoolObject[random.nextInt(100) + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.mergesort(array);
        int maxComparisons = (int) Math.ceil(array.length * Math.log(array.length) / Math.log(2.0));
        System.out.printf("Merge Sort: Max comparisons: %d%n", maxComparisons);
        System.out.printf("Merge Sort: Comparisons: %d%n", MyCoolObject.comparisons);
        assertFalse("Your merge sort is inefficient.", MyCoolObject.comparisons > maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests radix sort.
     * <p>
     * This suite does not test radix sort for efficiency.
     */
    @Test
    public void testRadixsortSortRandomArray() {
        // 1 to 100 length array
        int[] array = new int[random.nextInt(9) + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }
        int[] sorted = Arrays.copyOf(array, array.length);

        array = sorting.radixsort(array);
        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests worst case bubble sort.
     * <p>
     * There should be exactly n(n - 1)/2 comparisons.
     */
    @Test(timeout = 250)
    public void testWorstCaseBubbleSortArray() {
        MyCoolObject[] array = new MyCoolObject[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        //sArrays.sort(array, (o1, o2) -> -o1.compareTo(o2)); // sort descending
        MyCoolObject.comparisons = 0;
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.bubblesort(array);
        int maxComparisons = (array.length * (array.length - 1)) / 2;
        System.out.printf("Bubble Sort: Worst-Case expected: %d%n", maxComparisons);
        System.out.printf("Bubble Sort: Worst-Case actual: %d%n", MyCoolObject.comparisons);
        assertFalse("Your bubble sort worst-case didn't match.",
                MyCoolObject.comparisons != maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests worst case insertion sort.
     * <p>
     * There should be exactly n(n - 1)/2 comparisons.
     */
    @Test(timeout = 250)
    public void testWorstCaseInsertionSortArray() {
        MyCoolObject[] array = new MyCoolObject[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        //Arrays.sort(array, (o1, o2) -> -o1.compareTo(o2)); // sort descending
        MyCoolObject.comparisons = 0;
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.insertionsort(array);
        int maxComparisons = (array.length * (array.length - 1)) / 2;
        System.out.printf("Insertion Sort: Worst-Case expected: %d%n", maxComparisons);
        System.out.printf("Insertion Sort: Worst-Case actual: %d%n", MyCoolObject.comparisons);
        assertFalse("Your insertion sort worst-case didn't match.",
                MyCoolObject.comparisons != maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests worst case selection sort.
     * <p>
     * There should be exactly n(n - 1)/2 comparisons.
     */
    @Test(timeout = 250)
    public void testWorstCaseSelectionSortArray() {
        MyCoolObject[] array = new MyCoolObject[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        //Arrays.sort(array, (o1, o2) -> -o1.compareTo(o2)); // sort descending
        MyCoolObject.comparisons = 0;
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.selectionsort(array);
        int maxComparisons = (array.length * (array.length - 1)) / 2;
        System.out.printf("Insertion Sort: Worst-Case expected: %d%n", maxComparisons);
        System.out.printf("Insertion Sort: Worst-Case actual: %d%n", MyCoolObject.comparisons);
        assertFalse("Your insertion sort worst-case didn't match.",
                MyCoolObject.comparisons != maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests worst case quick sort.
     * <p>
     * There should be exactly n^2/2 comparisons (theoretical).
     * Due to differing implementations, this test checks that you're
     * within the expected value (not exactly equal).
     */
    @Test(timeout = 250)
    public void testWorstCaseQuickSortArray() {
        MyCoolObject[] array = new MyCoolObject[128];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        Arrays.sort(array);
        MyCoolObject.comparisons = 0;
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.quicksort(array, new Random() {
            @Override
            public int nextInt() {
                return 0;
            }

            @Override
            public int nextInt(int bound) {
                return 0;
            }
        }); // chosen by fair dice roll, guaranteed to be random.
        // RFC 1149.5 specifies 0 as the IEEE-vetted random number.
        // relevant xkcd: https://xkcd.com/221/
        int maxComparisons = ((array.length * array.length) / 2);
        System.out.printf("Quick Sort: Worst-Case expected: %d%n", maxComparisons);
        System.out.printf("Quick Sort: Worst-Case actual: %d%n", MyCoolObject.comparisons);
        assertFalse("Your quick sort worst-case didn't match.",
                MyCoolObject.comparisons > maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests worst case merge sort.
     * <p>
     * There should be at max n log2 n - 2^(log2 n) + 1 comparisons.
     */
    @Test(timeout = 250)
    public void testWorstCaseMergesortArray() {
        MyCoolObject[] array = new MyCoolObject[128];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        //Arrays.sort(array, (o1, o2) -> -o1.compareTo(o2));
        MyCoolObject.comparisons = 0;
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.mergesort(array);
        int maxComparisons = (int) (array.length * Math.ceil((Math.log(array.length) / Math.log(2)))
                - Math.pow(2, Math.ceil(Math.log(array.length) / Math.log(2))) + 1);
        System.out.printf("Merge Sort: Worst-Case expected: %d%n", maxComparisons);
        System.out.printf("Merge Sort: Worst-Case actual: %d%n", MyCoolObject.comparisons);
        assertFalse("Your merge sort worst-case didn't match.",
                MyCoolObject.comparisons > maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests already sorted case bubble sort.
     * <p>
     * There should be exactly n - 1 comparisons.
     */
    @Test(timeout = 250)
    public void testBubbleSortSortedArray() {
        MyCoolObject[] array = new MyCoolObject[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        Arrays.sort(array);
        MyCoolObject.comparisons = 0;
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.bubblesort(array);
        int maxComparisons = array.length - 1;
        System.out.printf("Bubble Sort: Sorted-Case expected: %d%n", maxComparisons);
        System.out.printf("Bubble Sort: Sorted-Case actual: %d%n", MyCoolObject.comparisons);
        assertFalse("Your bubble sort sorted-case didn't match.",
                MyCoolObject.comparisons != maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests already sorted case insertion sort.
     * <p>
     * There should be exactly n - 1 comparisons.
     */
    @Test(timeout = 250)
    public void testInsertionSortSortedArray() {
        MyCoolObject[] array = new MyCoolObject[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        Arrays.sort(array);
        MyCoolObject.comparisons = 0;
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.insertionsort(array);
        int maxComparisons = array.length - 1;
        System.out.printf("Insertion Sort: Sorted-Case expected: %d%n", maxComparisons);
        System.out.printf("Insertion Sort: Sorted-Case actual: %d%n", MyCoolObject.comparisons);
        assertFalse("Your insertion sort sorted-case didn't match.",
                MyCoolObject.comparisons != maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests already sorted case selection sort.
     * <p>
     * There should be exactly n(n-1)/2 comparisons.
     */
    @Test(timeout = 250)
    public void testSelectionSortSortedArray() {
        MyCoolObject[] array = new MyCoolObject[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        Arrays.sort(array);
        MyCoolObject.comparisons = 0;
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.selectionsort(array);
        int maxComparisons = array.length * (array.length - 1) / 2;
        System.out.printf("Selection Sort: Sorted-Case expected: %d%n", maxComparisons);
        System.out.printf("Selection Sort: Sorted-Case actual: %d%n", MyCoolObject.comparisons);
        assertFalse("Your selection sort sorted-case didn't match.",
                MyCoolObject.comparisons != maxComparisons);

        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests already sorted case quick sort.
     * <p>
     * Does not test efficiency.
     */
    @Test(timeout = 250)
    public void testQuickSortSortedArray() {
        MyCoolObject[] array = new MyCoolObject[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        Arrays.sort(array);
        MyCoolObject.comparisons = 0;
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.quicksort(array, random);
        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests already sorted case merge sort.
     * <p>
     * Does not test efficiency.
     */
    @Test(timeout = 250)
    public void testMergeSortSortedArray() {
        MyCoolObject[] array = new MyCoolObject[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = new MyCoolObject(random.nextInt());
        }
        Arrays.sort(array);
        MyCoolObject.comparisons = 0;
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.mergesort(array);
        Arrays.sort(sorted);
        assertArrayEquals(sorted, array);
    }

    /**
     * Tests to ensure bubble sort is stable.
     */
    @Test(timeout = 250)
    public void testBubbleSortIsStable() {
        MyCoolObject[] array = getStabilityTestArray();
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.bubblesort(array);
        Arrays.sort(sorted);

        assertArrayEquals(sorted, array);
    }

    /**
     * Tests to ensure insertion sort is stable.
     */
    @Test(timeout = 250)
    public void testInsertionSortIsStable() {
        MyCoolObject[] array = getStabilityTestArray();
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.insertionsort(array);
        Arrays.sort(sorted);

        assertArrayEquals(sorted, array);
    }

    /**
     * Tests to ensure merge sort is stable.
     */
    @Test(timeout = 250)
    public void testMergeSortIsStable() {
        MyCoolObject[] array = getStabilityTestArray();
        MyCoolObject[] sorted = Arrays.copyOf(array, array.length);

        sorting.mergesort(array);
        Arrays.sort(sorted);

        assertArrayEquals(sorted, array);
    }

    private MyCoolObject[] getStabilityTestArray() {
        MyCoolObject[] array = new MyCoolObject[6];
        array[0] = new MyCoolObject(1);
        array[1] = new MyCoolObject(2);
        array[2] = new MyCoolObject(3);
        array[3] = new MyCoolObject(4);
        array[4] = new MyCoolObject(4);
        array[5] = new MyCoolObject(6);
        array[3].myCoolString = "string 1";
        array[4].myCoolString = "string 2";
        return array;
    }

    private static class MyCoolObject implements Comparable<MyCoolObject> {

        public int myCoolNumber;
        public String myCoolString;

        public static int comparisons = 0;

        public MyCoolObject(int n) {
            myCoolNumber = n;
        }

        @Override
        public int compareTo(MyCoolObject o) {
            comparisons++;
            return Integer.compare(myCoolNumber, o.myCoolNumber);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MyCoolObject that = (MyCoolObject) o;

            return myCoolNumber == that.myCoolNumber &&
                    !(myCoolString != null ? !myCoolString.equals(that.myCoolString) : that.myCoolString != null);

        }

        @Override
        public int hashCode() {
            int result = myCoolNumber;
            result = 31 * result + (myCoolString != null ? myCoolString.hashCode() : 0);
            return result;
        }
    }
}