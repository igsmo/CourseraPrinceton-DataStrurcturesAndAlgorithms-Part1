/* *****************************************************************************
 *  Name:   Igor Smorag
 *  Date:   11/29/2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class implements a RandomizedQueue using a dynamic array.
 * The requirement of the problem is that each queue operation should take
 * at most cm iterations.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    /**
     * Dynamic array implementation to store all the RandomizedQueue values.
     */
    private Item[] dynamicArray;

    /**
     * Count of elements in the RandomizedQueue.
     */
    private int n = 0;

    /**
     * Iterator for RandomizedQueue.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {

        /**
         * Reference to the array being iterated over.
         */
        private Item[] arr;
        /**
         * Current index to be return on next() call.
         */
        private int currentIndex = 0;
        /**
         * Count of the array.
         */
        private int n;

        /**
         * Constructor for RandomizedQueueIterator.
         *
         * @param arr Array to iterate over.
         */
        RandomizedQueueIterator(Item[] arr, int n) {
            this.arr = arr;
            this.n = n;
        }

        /**
         * Checks if there is a next value to provide.
         *
         * @return True if next value exists, false otherwise.
         */
        public boolean hasNext() {
            return this.currentIndex != this.n;
        }

        /**
         * Gets the next element in the array.
         *
         * @return Next element in the array.
         */
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item reference = this.arr[this.currentIndex];
            this.currentIndex++;
            return reference;
        }

        /**
         * As per requirements, unsupported operation.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    /**
     * Contructor for RandomizedQueue. Creates an empty Item[] array.
     * Gives a warning: Unchecked cast: 'java.lang.Object[]' to 'Item[]'.
     * For explanation refer to the lecture:
     * https://www.coursera.org/learn/algorithms-part1/lecture/0URQC/generics
     */
    public RandomizedQueue() {
        this.dynamicArray = (Item[]) new Object[1];
    }


    private void resize(int size) {
        if (size < this.n) throw new UnsupportedOperationException();

        Item[] tempArray = (Item[]) new Object[size];
        for (int i = 0; i < this.n; i++) {
            tempArray[i] = this.dynamicArray[i];
        }
        this.dynamicArray = tempArray;
    }

    /**
     * Checks if an item is null. If yes, throws an exception.
     *
     * @param item Item to be checked for null.
     */
    private void checkForNullItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    /**
     * Checks if the Queue is empty. If yes, throws an exception.
     */
    private void checkForEmpty() {

        if (n <= 0)
            throw new NoSuchElementException();
    }


    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(this.dynamicArray, this.n);
    }

    /**
     * Checks if a RandomizedQueue is empty.
     *
     * @return True if it is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.n == 0;
    }

    /**
     * Gets the count of elements in the RandomizedQueue.
     *
     * @return Count of elements in the RandomizedQueue.
     */
    public int size() {
        return this.n;
    }


    /**
     * Adds an element to the RandomizedQueue.
     *
     * @param item Item to be added to the RandomizedQueue.
     */
    public void enqueue(Item item) {
        checkForNullItem(item);

        // If it is a new item, initialize the array to size 1.
        if (this.n == 0) {
            // Add a new object
            this.dynamicArray[0] = item;

        }
        else {
            // If the size is too large, increase the size
            if (this.n == this.dynamicArray.length)
                resize(2 * this.dynamicArray.length);

            // Add a new object
            this.dynamicArray[this.n] = item;
        }
        // Increase the count of element in an array
        this.n++;
    }

    /**
     * Removes a random item from the RandomizedQueue.
     *
     * @return A random item from the RandomizedQueue.
     */
    public Item dequeue() {
        checkForEmpty();

        // Make a reference to the object
        Item item = this.dynamicArray[StdRandom.uniformInt(this.n)];

        // Decrease the count of element in an array
        // Deletion of an element is the same as decrementing the size,
        // because anyway an empty value in the array takes the same size.
        this.n--;

        // As per lecture, we want an array to be contained within 25 and 100%,
        // so if it exceeds that, shrink the array.
        if (this.n <= (0.25 * this.dynamicArray.length))
            resize(this.dynamicArray.length / 2);

        return item;
    }

    /**
     * Peeks for a random item from the RandomizedQueue without deleting it.
     *
     * @return A random item from the RandomizedQueue.
     */
    public Item sample() {
        checkForEmpty();

        return this.dynamicArray[StdRandom.uniformInt(this.n)];
    }

    /**
     * Tester for all the public methods/classes.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();

        System.out.println("Size = " + randomizedQueue.isEmpty());

        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(42);
        randomizedQueue.enqueue(12);
        randomizedQueue.enqueue(64);
        randomizedQueue.enqueue(34);
        System.out.println("Size = " + randomizedQueue.size());

        System.out.println("Sample = " + randomizedQueue.sample());
        System.out.println("Size = " + randomizedQueue.size());

        System.out.println("Dequeue = " + randomizedQueue.dequeue());
        System.out.println("Dequeue = " + randomizedQueue.dequeue());
        System.out.println("Dequeue = " + randomizedQueue.dequeue());
        System.out.println("Dequeue = " + randomizedQueue.dequeue());
        System.out.println("Size = " + randomizedQueue.size());

        for (int el : randomizedQueue)
            System.out.println(el);

        System.out.println("Size = " + randomizedQueue.isEmpty());
    }
}
