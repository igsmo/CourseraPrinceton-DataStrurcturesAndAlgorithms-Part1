import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This Deque class is implemented using a LinkedList.
 *
 * @param <Item> Type of the item in the Deque.
 */
public class Deque<Item> implements Iterable<Item> {

    /**
     * First node in the Deque. Also called as "front".
     * previous property connects the last element in the LinkedList ("back").
     * There is a one-way connection between the first and last node making it a circular
     * LinkedList.
     */
    private Node first = null;

    /**
     * Count of elements in the Deque.
     */
    private int n = 0;

    /**
     * Node of a LinkedList.
     */
    private class Node {
        Item item;
        Node next;
        Node previous;

        public Node(Item item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    /**
     * Iterator class for iterating through the Deque using LinkedList implementation.
     */
    private class LinkedListIterator implements Iterator<Item> {

        /**
         * Active current node in the iterator.
         */
        private Node currentNode;

        /**
         * Constructor for class LinkedListIterator
         *
         * @param first First node.
         */
        public LinkedListIterator(Node first) {
            this.currentNode = first;
        }

        /**
         * Checks if the iterator has next value to provide.
         *
         * @return True if there exists a next value, false otherwise.
         */
        public boolean hasNext() {
            return this.currentNode != null;
        }

        /**
         * Proceeds to the next value of the iterator.
         *
         * @return Value of the old currentNode value.
         */
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            // Create reference to the old item value
            Item oldItem = this.currentNode.item;
            // Proceed to the next value
            this.currentNode = this.currentNode.next;

            return oldItem;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Constructor for a Deque class
     */
    public Deque() {
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
     * Checks if the Deque is empty. If yes, throws an exception.
     */
    private void checkForEmptyDeque() {
        if (n <= 0)
            throw new NoSuchElementException();
    }

    /**
     * Adds initial node in the Deque
     *
     * @param item Value of the node to be added as a first element.
     */
    private void addInitalNode(Item item) {

        /**
         * If the function is called, when there are no elements
         * in a linked list, adds a new first node.
         */

        this.first = new Node(item, null, null);

    }

    /**
     * Checks if a Deque is empty.
     *
     * @return True if it is empty, false otherwise.
     */
    public boolean isEmpty() {

        return this.n == 0;
    }

    /**
     * Gets the current count of elements in the Deque.
     *
     * @return Count of element in the Deque.
     */
    public int size() {
        return this.n;
    }

    /**
     * Adds an element to the front of the Deque.
     *
     * @param item Value of the element to be added to the front.
     */
    public void addFirst(Item item) {
        checkForNullItem(item);

        // If first is null, we should add an initial node
        if (this.first == null) {
            addInitalNode(item);
        }
        else {
            // Create new node. Previous property is set to the back of the LinkedList.
            Node tempNode = new Node(item, this.first, this.first.previous);
            // Assign new first node to the this.first
            this.first = tempNode;

            /* If it is a second node in the LinkedList, close the
             * circular LinkedList by setting the this.first.previous
             * to the last value.
             *
             * Handling the case where n != 1 is handled when creating a node,
             * because it is connected to this.(OLD)first.previous by default. */
            if (n == 1) {
                this.first.previous = this.first.next;
            }

            this.first.next.previous = this.first;
        }

        // Increment count of elements
        this.n++;
    }

    /**
     * Adds an element to the back of the Deque.
     *
     * @param item Value of the element to be added to the back.
     */
    public void addLast(Item item) {
        checkForNullItem(item);

        // If first and last are null, we should add an initial node
        if (this.first == null) {
            addInitalNode(item);
        }
        else {
            // Create new node. Previous property is set to the back of the LinkedList.
            Node tempNode = new Node(item, null, this.first.previous);

            /* If it is a second node in the LinkedList, the first node
             * has its previous property null, so it needs to be overridden
             * to the first node in the new node. */
            if (this.n == 1) {
                tempNode.previous = this.first;
            }

            // Assign new first node to the this.first.previous,
            // so the last node reference from the first node.
            this.first.previous = tempNode;

            // Assign reference from the old last node to the new last node.
            // If this.first.previous == null it means it is the first and only node.
            // In this case, skip.
            if (this.first.previous.previous != null)
                this.first.previous.previous.next = this.first.previous;

        }

        // Increment count of elements
        this.n++;
    }

    /**
     * Removes the element from the front of the Deque.
     *
     * @return Deleted element's value.
     */
    public Item removeFirst() {
        checkForEmptyDeque();

        // Create the reference to the element being removed
        Item oldFirstElement = this.first.item;

        // Assign the reference to the last node from the 2nd to the first node.
        if (this.first.next != null)
            this.first.next.previous = this.first.previous;

        // Assign a 2nd to the first node as the first node.
        // If the next node is null (only 1 el. in LinkedList), sets to null.
        this.first = this.first.next;

        // Decrement count of elements
        this.n--;

        return oldFirstElement;
    }

    /**
     * Removes the element from the back of the Deque.
     *
     * @return Deleted element's value.
     */
    public Item removeLast() {
        checkForEmptyDeque();

        Item oldLastElement;

        // If only one element in the list, set this.first to null.
        // It removes the whole LinkedList structure.
        if (n == 1) {
            oldLastElement = this.first.item;
            this.first = null;
        }
        else {
            oldLastElement = this.first.previous.item;

            // Assign the last node reference to the 2nd to last.
            this.first.previous = this.first.previous.previous;

            // Remove reference to the last node from the 2nd to last node.
            if (this.first.previous != null)
                this.first.previous.next = null;
        }

        // Decrement count of elements
        this.n--;

        return oldLastElement;
    }

    /**
     * Return an iterator over elements from front to back.
     *
     * @return An iterator from front to back.
     */
    public Iterator<Item> iterator() {
        return new LinkedListIterator(this.first);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        deque.addLast(1);
        deque.addLast(2);
        deque.removeLast();
        for (int item : deque)
            System.out.println(item);

        System.out.println("size " + deque.size());
        System.out.println("size " + deque.isEmpty());

    }
}