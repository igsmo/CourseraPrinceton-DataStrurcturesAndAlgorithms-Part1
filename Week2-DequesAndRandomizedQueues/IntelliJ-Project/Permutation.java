/* *****************************************************************************
 *  Name:   Igor Smorag
 *  Date:   11/29/2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {

        // Creates a new randomizedQueue
        RandomizedQueue<String> randomizedQueue =
                new RandomizedQueue<String>();

        // Reads the wanted number of k
        int k = 3;

        // Reads the data from the standard in until its empty
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            randomizedQueue.enqueue(input);
        }

        // Prints out k random elements
        for (int i = 0; i < k; i++)
            System.out.println(randomizedQueue.dequeue());
    }
}