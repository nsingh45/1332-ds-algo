import java.util.ArrayList;
import java.util.Random;

/**
  * Sorting implementation
  * CS 1332 : Fall 2014
  * @author Your Name Here
  * @version 1.0
  */
public class Sorting implements SortingInterface {

    // Do not add any instance variables.

    @Override
    public <T extends Comparable<? super T>> void bubblesort(T[] arr) {
        boolean swapped = false;
        do {
            swapped = false;
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i].compareTo(arr[i + 1]) > 0) {
                    arr = swap(arr, i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped);

    }

    @Override
    public <T extends Comparable<? super T>> void insertionsort(T[] arr) {
        int i = 0;
        int j = 0;
        for (i = 1; i < arr.length; i++) {
            j = i;
            while (j > 0 && arr[j].compareTo(arr[j - 1]) < 0) {
                arr = swap(arr, j, j - 1);
                j--;
            }
        }

    }

    @Override
    public <T extends Comparable<? super T>> void selectionsort(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            T min = arr[i];
            int ind = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(min) < 0) {
                    min = arr[j];
                    ind = j;
                }
            }
            arr = swap(arr, ind, i);
        }

    }

    @Override
    public <T extends Comparable<? super T>> void quicksort(T[] arr, Random r) {
        quicksortHelper(arr, 0, arr.length - 1, r);
    }

    /**
     * Recursively sorts the given array from ind1 to ind2
     * @param arr, the array
     * @param ind1
     * @param ind2
     * @param r
     */
    private <T extends Comparable<? super T>> void quicksortHelper(T[] arr,
            int ind1, int ind2, Random r) {
        if (ind2 - ind1 <= 1) {
            return;
        }
        int pivotIndex = r.nextInt((ind2 - 1) - ind1) + ind1;
        T pivot = arr[pivotIndex];
        int i = ind1;
        int j = ind2 - 1;
        swap(arr, ind2, pivotIndex);
        while (i < j) {
            if ((arr[i].compareTo(pivot) > 0)
                    && (arr[j].compareTo(pivot) < 0)) {
                swap(arr, i, j);
                i++;
                j--;
            } else if (arr[i].compareTo(pivot) <= 0) {
                i++;
            } else if (arr[j].compareTo(pivot) >= 0) {
                j--;
            }
        }
        if (arr[i].compareTo(pivot) > 0) {
            arr = swap(arr, i, ind2);
            quicksortHelper(arr, ind1, i - 1, r);
            quicksortHelper(arr, i + 1, ind2, r);
        } else {
            arr = swap(arr, i + 1, ind2);
            quicksortHelper(arr, ind1, i, r);
            quicksortHelper(arr, i + 2, ind2, r);
        }

    }

    @Override
    public <T extends Comparable<? super T>> void mergesort(T[] arr) {
        splitsies(arr);
    }

    /**
     * Splits the given array for MergeSort
     * @param arr, the array to split
     */
    private <T extends Comparable<? super T>> void splitsies(T[] arr) {
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        T[] leftArr = (T[]) new Comparable[arr.length / 2];
        T[] rightArr = (T[])
                new Comparable[(arr.length / 2) + (arr.length % 2)];
        for (int i = 0; i < arr.length / 2; i++) {
            leftArr[i] = arr[i];
        }
        int j = 0;
        for (int i = arr.length / 2; i < arr.length; i++) {
            rightArr[j] = arr[i];
            j++;
        }
        splitsies(leftArr);
        splitsies(rightArr);
        merge(leftArr, rightArr, arr);
    }

    /**
     * Merges the two given arrays into one large array
     */
    private <T extends Comparable<? super T>> T[] merge(T[] leftArr,
            T[] rightArr, T[] arr) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < leftArr.length || j < rightArr.length) {
            if (i < leftArr.length && j < rightArr.length) {
                if (leftArr[i].compareTo(rightArr[j]) <= 0) {
                    arr[k] = leftArr[i];
                    i++;
                    k++;
                } else if (leftArr[i].compareTo(rightArr[j]) > 0) {
                    arr[k] = rightArr[j];
                    j++;
                    k++;
                }
            } else if (i < leftArr.length) {
                arr[k] = leftArr[i];
                i++;
                k++;
            } else if (j < rightArr.length) {
                arr[k] = rightArr[j];
                j++;
                k++;
            }
        }
        return arr;
    }

    @Override
    public int[] radixsort(int[] arr) {
        ArrayList<Integer>[] buckets = new ArrayList[10];
        buckets[0] = new ArrayList<Integer>();
        ArrayList<Integer> extra = new ArrayList<Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                extra.add(Math.abs(arr[i]));
            } else if (buckets[arr[i] % 10] == null) {
                buckets[arr[i] % 10] = new ArrayList<Integer>();
                buckets[arr[i] % 10].add(arr[i]);
            } else {
                buckets[arr[i] % 10].add(arr[i]);
            }
        }
        int counter = 1;
        int div = 10;
        while (buckets[0].size() < arr.length) {
            int tracker = 0;
            for (int i = 0; i < counter; i++) {
                div = div * 10;
            }
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    for (int j : buckets[i]) {
                        arr[tracker] = j;
                        tracker++;
                    }
                    buckets[i].clear();
                }
            }
            for (int i = 0; i < arr.length; i++) {
                if (buckets[(arr[i] / (div / 10)) % div] == null) {
                    buckets[(arr[i] / (div / 10)) % div]
                        = new ArrayList<Integer>();
                }
                buckets[(arr[i] / (div / 10)) % div].add(arr[i]);
            }
            counter++;
        }
        counter = 0;
        if (!extra.isEmpty()) {
            int[] sortedNegatives = radixHelper(extra);
            for (int i = 0; i < sortedNegatives.length; i++) {
                arr[counter] = sortedNegatives[i];
                counter++;
            }
        }
        int i = 0;
        while (counter < arr.length) {
            arr[counter] = (buckets[0].get(i));
            counter++;
            i++;
        }
        return arr;
    }

    /**
     * Sorts all the negative values and returns a sorted array for RadixSort
     * @param al, an ArrayList of the negative integers
     * @return sortedNegatives, a sorted array of the negative integers
     */
    private <T extends Comparable<? super T>> int[]
    radixHelper(ArrayList<Integer> al) {
        int[] negatives = new int[al.size()];
        for (int i = 0; i < al.size(); i++) {
            negatives[i] = al.get(i);
        }
        negatives = radixsort(negatives);
        int[] sortedNegatives = new int[negatives.length];
        for (int i = 0; i < negatives.length; i++) {
            sortedNegatives[sortedNegatives.length - 1 - i] = negatives[i] * -1;
        }
        return sortedNegatives;
    }

    /**
     * Swaps two elements in the given array and returns an updated array
     * @param arr, the array
     * @param ind1, the index of the first element
     * @param ind2, the index of the second element
     * @return the updated array
     */
    private <T extends Comparable<? super T>> T[] swap(T[] arr,
            int ind1, int ind2) {
        T extra = arr[ind1];
        arr[ind1] = arr[ind2];
        arr[ind2] = extra;
        return arr;
    }
}
