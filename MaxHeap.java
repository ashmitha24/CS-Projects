import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Ashmitha Julius Aravind
 * @version 11.0
 * @userid aaravind7
 * @GTID 903700995
 *
 * Collaborators: N/A
 *
 * Resources: N/A
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     * 
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is not valid");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 1; i <= data.size(); i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException("Data is not valid");
            }
            backingArray[i] = data.get(i - 1);
            size++;
        }
        int nonLeaf = size / 2;
        for (int i = nonLeaf; i > 0; i--) {
            rHeapify(i);
        }

    }


    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is not valid");
        }
        if (backingArray.length == (size + 1)) {
            T[] array = (T[]) new Comparable[2 * backingArray.length];
            for (int i = 0; i < size; i++) {
                array[i] = backingArray[i];
            }
            array[size + 1] = data;
            backingArray = array;
        } else {
            backingArray[size + 1] = data;
        }
        size++;

        T parent = backingArray[1];
        int i = size;
        int parentIndex = 1;
        if (i > 3) {
            parent = backingArray[i / 2];
            parentIndex = i / 2;
        }
        while (data.compareTo(parent) > 0) {
            backingArray[i] = parent;
            parent = data;
            backingArray[parentIndex] = data;
            if (i > 3) {
                i = i / 2;
                parent = backingArray[i / 2];
                parentIndex = i / 2;
            }
        }
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        T root = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        rHeapify(1);
        return root;
    }

    /**
     * Helper method to heapify
     * @param i the current index
     */
    public void rHeapify(int i) {
        int parent = i;
        int left = 2 * i;
        int right = 2 * i + 1;

        if (left <= size && backingArray[left].compareTo(backingArray[parent]) > 0) {
            parent = left;
        }
        if (right <= size && backingArray[right].compareTo(backingArray[parent]) > 0) {
            parent = right;
        }
        if (parent > i) {
            T large = backingArray[i];
            backingArray[i] = backingArray[parent];
            backingArray[parent] = large;
            rHeapify(parent);
        }
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
