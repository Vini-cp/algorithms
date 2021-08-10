import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        addCheck(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        removeCheck();
        // int element = StdRandom.uniform(size);
        int element = 0;
        StdOut.println(element);
        if (element == 0)
            return removeFirst();
        else if (element == (size - 1))
            return removeLast();
        else
            return removeMiddle(element);
    }

    // return a random item (but do not remove it)
    public Item sample() {
        removeCheck();
        Node node = first;
        Item item;
        int element = StdRandom.uniform(size);
        while (element-- > 0)
            first = first.next;
        item = first.item;
        first = node;
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void addCheck(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Argument could not be null.");
    }

    private void removeCheck() {
        if (isEmpty())
            throw new NoSuchElementException("There is no element to remove.");
    }

    private Item removeFirst() {
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    private Item removeLast() {
        Node node = first;
        Item item;
        int i = 0;
        while (++i < size - 1)
            first = first.next;
        item = first.next.item;
        first.next = null;
        first = node;
        size--;
        return item;
    }

    private Item removeMiddle(int element) {
        Node node = first;
        Item item;
        int i = 0;
        while (++i <= element - 1)
            first = first.next;
        item = first.next.item;
        first.next = first.next.next;
        first = node;
        size--;
        return item;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Invalid operation for Deques.");
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException("There is no more elements.");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> str = new RandomizedQueue<String>();
        StdOut.println(str.isEmpty());
        // str.removeFirst();
        str.enqueue("Test0");
        str.enqueue("Test1");
        StdOut.println(str.size());
        str.enqueue("Test2");
        str.enqueue("Test3");
        // str.addLast(null);

        StdOut.println(str.isEmpty());
        StdOut.println(str.size());

        StdOut.println();
        Iterator<String> it = str.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }

        StdOut.println();

        StdOut.println(str.sample());
        StdOut.println(str.sample());
        StdOut.println(str.sample());
        StdOut.println(str.sample());
        StdOut.println(str.sample());
        StdOut.println(str.sample());
        StdOut.println(str.size());

        StdOut.println();

        Iterator<String> it1 = str.iterator();
        while (it1.hasNext()) {
            StdOut.println(it1.next());
        }

        StdOut.println();

        StdOut.println(str.dequeue());
        // StdOut.println(str.dequeue());

        StdOut.println();

        Iterator<String> it2 = str.iterator();
        while (it2.hasNext()) {
            StdOut.println(it2.next());
        }

        // StdOut.println(str.size());
        // StdOut.println();
        //
        // StdOut.println(str.dequeue());
        //
        // StdOut.println();

        Iterator<String> it3 = str.iterator();
        while (it3.hasNext()) {
            StdOut.println(it3.next());
        }

        StdOut.println(str.size());


        // StdOut.println();
        // StdOut.println(str.removeFirst());
        // StdOut.println(str.size());
        // StdOut.println(str.removeLast());
        // StdOut.println(str.size());
        // StdOut.println();
        //
        // it = str.iterator();
        // while (it.hasNext()) {
        //     StdOut.println(it.next());
        // }
    }
}
