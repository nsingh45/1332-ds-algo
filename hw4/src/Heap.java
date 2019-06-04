public class Heap<T extends Comparable<? super T>> implements HeapInterface<T>,
       Gradable<T> {

    private int numItems;
    private T[] arr;
    
    public Heap() {
        numItems = 0;
        arr = (T[]) (new Comparable[10]);
        arr[0] = null;
    }
    
    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (arr.length <= size() + 1) {
            resize();
        }
        arr[numItems + 1] = item;
        heapifyUp(numItems + 1);
        numItems++;
    }

    @Override
    public boolean isEmpty() {
        return numItems == 0;
    }

    @Override
    public T peek() {
        return arr[1];
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            return null;
        }
        T min = arr[1];
        arr[1] = arr[numItems];
        arr[numItems] = null;
        heapifyDown(1);
        numItems--;
        return min;
    }

    @Override
    public int size() {
        return numItems;
    }

    @Override
    public T[] toArray() {
        return arr;
    }
    
    /**
     * Swaps the items at ind1 and ind2 in the backing array
     * @param ind1, the first item to be swapped
     * @param ind2, the second item to be swapped
     */
    private void swap(int ind1, int ind2) {
        T firstItem = arr[ind1];
        T secondItem = arr[ind2];
        arr[ind2] = firstItem;
        arr[ind1] = secondItem;
    }
    
    /**
     * Doubles the size of the backing array and copies all items over
     */
    private void resize() {
        T[] bigArr = (T[]) (new Comparable[arr.length * 2]);
        for (int i = 1; i < arr.length; i++) {
            bigArr[i] = arr[i];
        }
        arr = bigArr;
    }
    
    /**
     * Recursively "bubbles down" an item to maintain the order of the heap
     * @param index, the index of the item to be reordered
     */
    private void heapifyDown(int index) {
        int leastChild = 0;
        if (index >= arr.length / 2) {
            return;
        } else {
            int comp = (arr[index * 2].compareTo(arr[index * 2 + 1]));
            if (comp > 0) {
                leastChild = index * 2 + 1;
            } else if (comp <= 0) {
                leastChild = index * 2;
            }
        }
        if (arr[index].compareTo(arr[leastChild]) > 0) {
            swap(index, leastChild);
            heapifyDown(leastChild);
        }
        
    }
    
    /**
     * Recursively "bubbles up" a newly added item to maintain the order of the heap
     * @param index, the index of the item to be reordered
     */
    private void heapifyUp(int index) {
        if (index <= 1 || arr[index] == null) {
            return;
        }
        if (arr[index].compareTo(arr[index / 2]) <= 0) {
            swap(index, index / 2);
            heapifyUp(index / 2);
        }
    }
    
}
